package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import util.WrapperMessage;

import java.util.*;
import java.util.Map;

/**
 * Created by cloudera on 7/7/17.
 */
public class ReduceByKey implements Transformation {
    @Override
    public JavaPairDStream<String, WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaPairDStream<String, WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Take prevPid = " + prevPid);
        JavaPairDStream<String,WrapperMessage> prevDStream = prevDStreamMap.get(prevPid);

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        //operator can be ReduceByKey or ReduceByKeyandWindow
        String operator = filterProperties.getProperty("operator");
        String executorPlugin = filterProperties.getProperty("executor-plugin");

        JavaPairDStream<String,WrapperMessage> outputDStream = prevDStream;
        if(operator.equalsIgnoreCase("Reduce")){
            //outputDStream = prevDStream.reduceByKey();
        }
        else{
            String windowType = filterProperties.getProperty("window-type");
            String windowDurationString = filterProperties.getProperty("window-duration");
            String slideDurationString = filterProperties.getProperty("slide-duration");

           // outputDStream = prevDStream.reduceByKeyAndWindow();
        }
        return outputDStream;
    }
}
