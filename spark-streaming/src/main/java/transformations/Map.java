package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import scala.Tuple2;
import util.WrapperMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by cloudera on 7/7/17.
 */
public class Map implements Transformation {
    @Override
    public JavaPairDStream<String, WrapperMessage> transform(JavaRDD emptyRDD, java.util.Map<Integer, JavaPairDStream<String, WrapperMessage>> prevDStreamMap, java.util.Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Take prevPid = " + prevPid);
        JavaPairDStream<String,WrapperMessage> prevDStream = prevDStreamMap.get(prevPid);
        JavaPairDStream<String,Row> prevRowDstream = prevDStream.mapValues(s -> s.getRow());

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        String mapper = filterProperties.getProperty("mapper");
        JavaPairDStream<String,WrapperMessage> finalDStream = null ;

        if(mapper.equalsIgnoreCase("IdentityMapper")){
            finalDStream = prevDStream;
        }
        else {
            String executorPlugin = filterProperties.getProperty("executor-plugin");
            try {
                Class userClass =  Class.forName(executorPlugin);
                Function function = (Function) userClass.newInstance();
                JavaDStream<Row> rowRdd = prevRowDstream.map(function);
                finalDStream = rowRdd.mapToPair(s -> new Tuple2<String, WrapperMessage>(null,new WrapperMessage(s)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        finalDStream.print();
        return finalDStream;
    }
}
