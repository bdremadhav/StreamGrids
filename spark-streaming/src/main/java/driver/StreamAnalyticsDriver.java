package driver;

import com.wipro.ats.bdre.GetParentProcessType;
import com.wipro.ats.bdre.md.api.GetProcess;
import com.wipro.ats.bdre.md.api.GetProperties;
import com.wipro.ats.bdre.md.api.InstanceExecAPI;
import com.wipro.ats.bdre.md.api.StreamingMessagesAPI;
import com.wipro.ats.bdre.md.beans.ProcessInfo;
import com.wipro.ats.bdre.md.dao.jpa.InstanceExec;
import com.wipro.ats.bdre.md.dao.jpa.Messages;
import datasources.Source;
import emitters.Emitter;
import messageformat.Parser;
import messageschema.SchemaReader;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import persistentstores.PersistentStore;
import transformations.Transformation;

import java.io.Serializable;
import java.util.*;


/**
 * Created by cloudera on 5/18/17.
 */
public class StreamAnalyticsDriver implements Serializable {

    public static final Logger LOGGER = Logger.getLogger(StreamAnalyticsDriver.class);
    public static final String SOURCEPACKAGE = "datasources.";
    public static final String TRANSFORMATIONSPACKAGE = "transformations.";
    public static final String EMITTERPACKAGE = "emitters.";
    public static final String PERSISTENTSTOREPACKAGE = "persistentstores.";

    public static Map<Integer, Set<Integer>> prevMap = new HashMap<>();
    public static List<Integer> listOfSourcePids = new ArrayList<>();
    public static List<Integer> listOfTransformations = new ArrayList<>();
    public static List<Integer> listOfEmitters = new ArrayList<>();
    public static List<Integer> listOfPersistentStores = new ArrayList<>();
    public static Map<Integer, String> nextPidMap = new HashMap<Integer, String>();
    public static Map<Integer,String> pidMessageTypeMap = new HashMap<>();
    public static Integer parentProcessId;
    static int countEmitterCovered = 0;
    static Time batchStartTime = new Time(0);
    static Map<Integer,JavaDStream<String>> pidDStreamMap= new HashMap<>();
    public Map<Integer, JavaDStream<Row>> transformedDStreamMap = new HashMap<>();
    Integer sourcePid = 0;

    public static void main(String[] args) {

           Integer parentProcessId = Integer.parseInt(args[0]);
        try {
            String username = (args[1]);
            GetProcess getProcess = new GetProcess();
            String[] processDetailsArgs = new String[]{"-p", args[0], "-u", username};
            List<ProcessInfo> subProcessList = getProcess.execute(processDetailsArgs);
            for (ProcessInfo processInfo : subProcessList) {
                nextPidMap.put(processInfo.getProcessId(), processInfo.getNextProcessIds());
                GetParentProcessType getParentProcessType = new GetParentProcessType();
                String processTypeName = getParentProcessType.processTypeName(processInfo.getProcessId());

                //TODO: Use processtype map here instead of string startswith
                if (processTypeName.startsWith("source")) {
                    listOfSourcePids.add(processInfo.getProcessId());
                    GetProperties getProperties = new GetProperties();
                    Properties properties = getProperties.getProperties(processInfo.getProcessId().toString(), "message");
                    String messageName = properties.getProperty("messageName");
                    LOGGER.info("messagename is " + messageName);
                    StreamingMessagesAPI streamingMessagesAPI = new StreamingMessagesAPI();
                    Messages messages = streamingMessagesAPI.getMessage(messageName);
                    pidMessageTypeMap.put(processInfo.getProcessId(), messages.getFormat());

                }
                if (processTypeName.startsWith("operator")) {
                    listOfTransformations.add(processInfo.getProcessId());
                }
                if (processTypeName.startsWith("emitter")) {
                    listOfEmitters.add(processInfo.getProcessId());
                }
                if (processTypeName.startsWith("persistentStore")) {
                    listOfPersistentStores.add(processInfo.getProcessId());
                }

            }

            List<Integer> currentUpstreamList = new ArrayList<>();
            currentUpstreamList.addAll(listOfSourcePids);
            //populate prevMap with source pids,
            for (Integer sourcePid : listOfSourcePids) {
                prevMap.put(sourcePid, null);
            }
            // Create a Spark Context.
            //TODO get appname properties from parent process
            SparkConf conf = new SparkConf().setAppName("Log Analyzer");
            JavaSparkContext sc = new JavaSparkContext(conf);
            Broadcast<Map<Integer, String>> broadcastVar = sc.broadcast(pidMessageTypeMap);
            String applicationId = sc.sc().applicationId();
            System.out.println("applicationId = " + applicationId);
            InstanceExecAPI instanceExecAPI = new InstanceExecAPI();
            InstanceExec instanceExec = new InstanceExec();
            instanceExecAPI.insertInstanceExec(parentProcessId, applicationId);

            long batchDuration = 30000;

            GetProperties getProperties = new GetProperties();
            Properties properties = getProperties.getProperties(parentProcessId.toString(), "batchDuration");
            //TODO get batchduration properties from parent process
            if (properties.getProperty("batchDuration") != null)
                batchDuration = Long.valueOf(properties.getProperty("batchDuration"));

            JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(batchDuration));
            StreamAnalyticsDriver streamAnalyticsDriver = new StreamAnalyticsDriver();


            //iterate till the list contains only one element and the element must be the parent pid indicating we have reached the end of pipeline
            while (!currentUpstreamList.isEmpty()) {
                System.out.println("currentUpstreamList = " + currentUpstreamList);

                System.out.println("prevMap = " + prevMap);
                streamAnalyticsDriver.identifyFlows(currentUpstreamList, nextPidMap, parentProcessId);
            }
            streamAnalyticsDriver.createDStreams(ssc, listOfSourcePids);
            streamAnalyticsDriver.invokeDStreamOperations(ssc, listOfSourcePids, prevMap, nextPidMap, broadcastVar);
            ssc.start();
            System.out.println(" streaming started");
            ssc.awaitTermination();
        }catch (Exception e){
            LOGGER.info("final exception = " + e);
            InstanceExecAPI instanceExecAPI = new InstanceExecAPI();
            instanceExecAPI.updateInstanceExec(parentProcessId);
            e.printStackTrace();
        }
    }
    public void createDStreams(JavaStreamingContext ssc,List<Integer> listOfSourcePids) throws Exception{
        try {
            System.out.println(" Inside create dstreams");
        for (Integer pid : listOfSourcePids) {
            GetParentProcessType getParentProcessType=new GetParentProcessType();
            String processTypeName=getParentProcessType.processTypeName(pid);
            String sourceType = processTypeName.replace("source_","");
            String sourceClassName = SOURCEPACKAGE+sourceType+"Source";

                Class sourceClass = Class.forName(sourceClassName);
                Source sourceObject = (Source)sourceClass.newInstance();
                JavaDStream<String> javaDStream = sourceObject.execute(ssc, pid);
                pidDStreamMap.put(pid,javaDStream);
            }
        }catch (Exception e){
            LOGGER.info(e);
            e.printStackTrace();
            throw  e;
        }
    }

    public void identifyFlows(List<Integer> currentUpstreamList, Map<Integer, String> nextPidMap,Integer parentProcessId) throws Exception {
        try {
            //prevMapTemp holds the prev ids only for pids involved in current iteration
            Map<Integer, Set<Integer>> prevMapTemp = new HashMap<>();
            for (Integer currentPid : currentUpstreamList) {

                String nextPidString = nextPidMap.get(currentPid);
                //splitting next process id with comma into string array
                String[] nextPidArray = nextPidString.split(",");
                //new array of integers to hold next pids
                int[] nextPids = new int[nextPidArray.length];
                //iterate through the string array to construct the prev map
                for (int i = 0; i < nextPidArray.length; i++) {
                    //cast String to Integer
                    nextPids[i] = Integer.parseInt(nextPidArray[i]);
                    //populate prevMap with nextPid of currentPid
                    new StreamAnalyticsDriver().add(nextPids[i], currentPid, prevMapTemp);
                    new StreamAnalyticsDriver().add(nextPids[i], currentPid, prevMap);
                }
            }

            //update the currentUpstreamList with the keys of the prevMap i.e all unique next ids of current step will be upstreams of following iteration
            currentUpstreamList.clear();
            //if the set contains parentProcessId, remove it
            if (prevMapTemp.containsKey(parentProcessId))
                prevMapTemp.remove(parentProcessId);
            currentUpstreamList.addAll(prevMapTemp.keySet());
        }catch (Exception e){
            LOGGER.info(e);
            e.printStackTrace();
            throw  e;
        }
    }

    //method to add previous pids as values to list against given process-id as key
    public void add(Integer key, Integer newValue, Map<Integer, Set<Integer>> prevMap) throws Exception {
        try{
        Set<Integer> currentValue = prevMap.get(key);
        if (currentValue == null) {
            currentValue = new HashSet<>();
            prevMap.put(key, currentValue);
        }
        currentValue.add(newValue);
    }catch (Exception e){
        LOGGER.info(e);
        e.printStackTrace();
        throw  e;
    }
    }

    //convert JavaDStream<String> to JavaDStream<Row>
    public JavaDStream<Row> convertToDstreamRow(JavaDStream<String> stringJavaDStream, Integer pid){

        JavaDStream<Row> rowJavaDStream = stringJavaDStream.map(new Function<String, Row>() {
            @Override
            public Row call(String record) throws Exception {
                return RowFactory.create(Parser.parseMessage(record, pid));
            }
        });
        return rowJavaDStream;
    }


    //this method invokes DStream operations based on the prev map & handles logic accordingly for source/transformation/emitter
    public void invokeDStreamOperations(JavaStreamingContext ssc, List<Integer> listOfSourcePids, Map<Integer, Set<Integer>> prevMap, Map<Integer, String> nextPidMap, Broadcast<Map<Integer,String>> broadcastVar) throws Exception {
        System.out.println(" inside invoke dstream");
            System.out.println("prevMap = " + prevMap);
            //iterate through each source and create respective dataFrames for sources
            for (Integer pid : pidDStreamMap.keySet()) {
                System.out.println("pid = " + pid);
                System.out.println("FetchingDStream for source pid= " + pid);
                JavaDStream<String> msgDataStream = pidDStreamMap.get(pid);
                JavaDStream<Row> rowMsgDataStream = convertToDstreamRow(msgDataStream,pid);

                transformedDStreamMap.put(pid,rowMsgDataStream);
                System.out.println("transformedDStreamMap = " + transformedDStreamMap);
                SchemaReader schemaReader = new SchemaReader();
                StructType schema = schemaReader.generateSchema(pid);
                System.out.println("schema.toString() = " + schema.toString());
                rowMsgDataStream.foreachRDD(new Function<JavaRDD<Row>, Void>() {
                    @Override
                    public Void call(JavaRDD<Row> rowJavaRDD) throws Exception {
                        System.out.println("rowJavaRDD = " + rowJavaRDD);
                        transformAndEmit(nextPidMap.get(pid), transformedDStreamMap,schema );
                        return null;
                    }
                });

                return;
            }
    }



    public void transformAndEmit(String nextPidString, Map<Integer, JavaDStream<Row>> transformedDStreamMap,StructType schema) throws Exception{
        try {

            System.out.println("nextPidString = " + nextPidString);
            System.out.println("transformedDStreamMap = " + transformedDStreamMap);
            if (!nextPidString.equals(nextPidMap.get(parentProcessId))) { //condition occurs when all emitters are finished and next is set to parentprocessid
                String[] nextPidStringArray = nextPidString.split(",");
                Integer[] nextPidInts = new Integer[nextPidStringArray.length];
                for (int i = 0; i < nextPidStringArray.length; i++) {
                    //cast String to Integer
                    nextPidInts[i] = Integer.parseInt(nextPidStringArray[i]);
                    System.out.println("nextPidInts[i] = " + nextPidInts[i]);
                    if (nextPidInts[i].equals(parentProcessId)) {
                        countEmitterCovered++;
                        System.out.println("No.of Emitters covered =" + countEmitterCovered);
                        if (countEmitterCovered >= listOfEmitters.size()) {
                            System.out.println("clearing contents of transformedDStreamMap before clearing= " + transformedDStreamMap);
                            transformedDStreamMap.clear();
                            System.out.println("clearing contents of transformedDStreamMap before clearing= " + transformedDStreamMap);
                            System.out.println("resetting countEmitterCovered");
                            countEmitterCovered = 0;
                            return;
                        }
                    }
                }
                for (int i = 0; i < nextPidInts.length; i++) {
                    for (Integer prevPid : prevMap.get(nextPidInts[i])) {
                        if (transformedDStreamMap.get(prevPid) == null) {
                            return;
                        }
                    }
                }
                for (Integer pid : nextPidInts) {
                    System.out.println("pid for transformation or emitter= " + pid);

                    System.out.println("listOfTransformations = " + listOfTransformations);
                    if (listOfTransformations.contains(pid)) {
                        System.out.println(" inside transformation ");
                        //this pid is of type transformation, find prev pids to output the appropriate dataframe
                        Set<Integer> prevPids = prevMap.get(pid);
                        System.out.println("prevMap = " + prevMap);

                        GetParentProcessType getParentProcessType = new GetParentProcessType();
                        String transformationType = getParentProcessType.processTypeName(pid).replace("operator_", "");
                        String transformationClassName = TRANSFORMATIONSPACKAGE + transformationType;

                            Class transformationClass = Class.forName(transformationClassName);
                            Transformation transformationObject = (Transformation) transformationClass.newInstance();
                            JavaDStream<Row> dStreamPostTransformation = transformationObject.transform(transformedDStreamMap, prevMap, pid,schema);
                            transformedDStreamMap.put(pid, dStreamPostTransformation);

                    }
                    if (listOfEmitters.contains(pid)) {
                        //found emitter node, so get upstream pid and persist based on emitter
                        Set<Integer> prevPids = prevMap.get(pid);
                        int count = 0;
                        for (Integer prevPid : prevPids) {
                            count++;
                            System.out.println("count = " + count);
                            System.out.println("currently trying to emit dstream of prevPid = " + prevPid);
                            JavaDStream<Row> prevDStream = transformedDStreamMap.get(prevPid);

                            GetParentProcessType getParentProcessType = new GetParentProcessType();
                            String emitterType = getParentProcessType.processTypeName(pid).replace("emitter_", "");
                            String emitterClassName = EMITTERPACKAGE + emitterType + "Emitter";

                                Class emitterClass = Class.forName(emitterClassName);
                                Emitter emitterObject = (Emitter) emitterClass.newInstance();
                                emitterObject.persist(prevDStream, pid, prevPid,schema);

                            //pidDataFrameMap.remove(prevPid);
                        }
                        //pidDataFrameMap.clear();
                    }

                    if (listOfPersistentStores.contains(pid)) {
                        //found emitter node, so get upstream pid and persist based on emitter
                        Set<Integer> prevPids = prevMap.get(pid);
                        int count = 0;
                        for (Integer prevPid : prevPids) {
                            count++;
                            System.out.println("count = " + count);
                            System.out.println("currently trying to persist dstream of prevPid = " + prevPid);
                            JavaDStream<Row> prevDStream = transformedDStreamMap.get(prevPid);

                            GetParentProcessType getParentProcessType = new GetParentProcessType();
                            String persistentStoreType = getParentProcessType.processTypeName(pid).replace("persistentStore_", "");
                            String persistentStoreClassName = PERSISTENTSTOREPACKAGE + persistentStoreType + "PersistentStore";

                                Class persistentStoreClass = Class.forName(persistentStoreClassName);
                                PersistentStore persistentStoreObject = (PersistentStore) persistentStoreClass.newInstance();
                                persistentStoreObject.persist(prevDStream, pid, prevPid, schema);

                            //pidDataFrameMap.remove(prevPid);
                        }
                        //pidDataFrameMap.clear();
                    }


                    transformAndEmit(nextPidMap.get(pid), transformedDStreamMap,schema);
                }
            }

        } catch (Exception e) {
            LOGGER.info(e);
            e.printStackTrace();
            throw e;
        }
    }

}

