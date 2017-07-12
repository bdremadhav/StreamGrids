package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.api.java.JavaDStream;
import util.WrapperMessage;

import java.util.*;

/**
 * Created by cloudera on 7/5/17.
 */
public class Persist implements Transformation{
    @Override
    public JavaDStream<WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaDStream<WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid1 = prevPidList.get(0);
        System.out.println("Inside persist prevPid1 = " + prevPid1);
        JavaDStream<WrapperMessage> inputDStream = prevDStreamMap.get(prevPid1);
        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        String storageLevelString = filterProperties.getProperty("storage-level");
        StorageLevel storageLevel = new StorageLevel().fromString(storageLevelString);

        JavaDStream<WrapperMessage> finalDStream = inputDStream;
        if(inputDStream != null){
            finalDStream = inputDStream.persist(storageLevel);
        }
        return finalDStream;
    }
}
