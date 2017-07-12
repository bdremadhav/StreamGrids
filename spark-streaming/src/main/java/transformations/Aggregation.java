package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.h2.mvstore.DataUtils;
import util.WrapperMessage;

import java.util.*;

/**
 * Created by cloudera on 7/4/17.
 */
public class Aggregation implements Transformation{



    @Override
    public JavaDStream<WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaDStream<WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Aggregation prevPid = " + prevPid);
        JavaDStream prevDStream = prevDStreamMap.get(prevPid);

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");

        //TODO: In ui, map should be of this format: Map("col1" -> "max", "col2" -> "avg", "col3" -> "sum", "col4" -> "min")
        Map<String, String> fieldAggrMap = new HashMap<>();

        JavaDStream<WrapperMessage> finalDStream = prevDStream.transform(new Function<JavaRDD<WrapperMessage>, JavaRDD<WrapperMessage>>() {
            @Override
            public JavaRDD<WrapperMessage> call(JavaRDD<WrapperMessage> rddWrapperMessage) throws Exception {
                JavaRDD<Row> rddRow = rddWrapperMessage.map(record -> WrapperMessage.convertToRow(record));
                SQLContext sqlContext = SQLContext.getOrCreate(rddWrapperMessage.context());
                DataFrame dataFrame = sqlContext.createDataFrame(rddRow, schema);
                DataFrame aggregatedDF = null;

                if (dataFrame != null && !dataFrame.rdd().isEmpty()) {

                        System.out.println("showing dataframe before filter ");
                        dataFrame.show(100);
                        aggregatedDF = dataFrame.agg(fieldAggrMap);
                        aggregatedDF.show(100);
                        System.out.println("showing dataframe after filter ");

                }
                JavaRDD<WrapperMessage> finalRDD = emptyRDD;
                if (aggregatedDF != null)
                    finalRDD = aggregatedDF.javaRDD().map(s -> WrapperMessage.convertToWrapperMessage(s));
                return finalRDD;
            }
        });
        return finalDStream;

    }
}
