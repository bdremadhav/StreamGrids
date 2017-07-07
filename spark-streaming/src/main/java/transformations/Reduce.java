package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import scala.Tuple2;
import util.WrapperMessage;

import java.util.*;
import java.util.Map;

/**
 * Created by cloudera on 7/7/17.
 */
public class Reduce implements Transformation {
    @Override
    public JavaPairDStream<String, WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaPairDStream<String, WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Take prevPid = " + prevPid);
        JavaPairDStream<String,WrapperMessage> prevDStream = prevDStreamMap.get(prevPid);
        JavaDStream<WrapperMessage> dStream = prevDStream.map(s -> s._2);

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        //operator can be Reduce or ReduceByWindow
        String operator = filterProperties.getProperty("operator");
        String executorPlugin = filterProperties.getProperty("executor-plugin");
        JavaDStream<WrapperMessage> outputDStream = dStream;
        if(operator.equalsIgnoreCase("Reduce")){
            //outputDStream = dStream.reduce();
        }
        else{
            String windowType = filterProperties.getProperty("window-type");
            String windowDurationString = filterProperties.getProperty("window-duration");
            String slideDurationString = filterProperties.getProperty("slide-duration");

            //outputDStream = dStream.reduceByWindow();
        }

        return outputDStream.mapToPair(s -> new Tuple2<String, WrapperMessage>(null,s));

    }
}
