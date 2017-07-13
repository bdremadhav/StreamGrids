package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import scala.Tuple2;
import util.WrapperMessage;

import java.util.*;
import java.util.Map;

/**
 * Created by cloudera on 7/7/17.
 */
public class FlatMap implements Transformation {
    @Override
    public JavaPairDStream<String, WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaPairDStream<String, WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Take prevPid = " + prevPid);
        JavaPairDStream<String,WrapperMessage> prevDStream = prevDStreamMap.get(prevPid);

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        String operator = filterProperties.getProperty("operator");
        String mapper = filterProperties.getProperty("flat-mapper");

        JavaPairDStream<String,WrapperMessage> finalDStream = null ;
        if(mapper.equalsIgnoreCase("IdentityMapper")){
            finalDStream = prevDStream;
        }
        else {
            String executorPlugin = filterProperties.getProperty("executor-plugin");
            //TODO: Execute user given executor plugin
            finalDStream = prevDStream;
        }
        return finalDStream;

    }
}
