package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import scala.Tuple2;
import util.WrapperMessage;

import java.util.*;

/**
 * Created by cloudera on 7/6/17.
 */
public class MapToPair implements Transformation{
    @Override
    public JavaPairDStream<String,WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaPairDStream<String,WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
       /* List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid1 = prevPidList.get(0);
        System.out.println("Inside MapToPair prevPid1 = " + prevPid1);
        JavaPairDStream<String,WrapperMessage> inputDStream = prevDStreamMap.get(prevPid1);
        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        String keyString = filterProperties.getProperty("key-fields");
        String[] keyFields = keyString.split(",");


        JavaDStream<WrapperMessage> dStream = inputDStream.map(s -> s._2);
        JavaDStream<WrapperMessage> finalDStream = dStream.transform(new Function<JavaRDD<WrapperMessage>, JavaRDD<WrapperMessage>>() {
            @Override
            public JavaRDD<WrapperMessage> call(JavaRDD<WrapperMessage> rddWrapperMessage) throws Exception {
                JavaRDD<Row> rddRow = rddWrapperMessage.map(record -> WrapperMessage.convertToRow(record));
                JavaPairRDD<String,Row> pairRDD = rddRow.mapToPair(new PairFunction<Row, String, Row>() {
                    @Override
                    public Tuple2<String, Row> call(Row row) throws Exception {
                        String key = null;
                        if (row != null) {
                            for(String keyField : keyFields){
                                key = row.getString(1) + "#";
                            }
                        }
                        return new Tuple2<String, Row>(key,row);

                    }
                });

                JavaRDD<WrapperMessage> finalRDD = emptyRDD;
                if(pairRDD != null)
                    finalRDD = pairRDD.map((k,v) -> WrapperMessage.convertToPairWrapperMessage(k,v));
                return finalRDD;
            }
        });
        return finalDStream;
        */
        return null;
    }
}
