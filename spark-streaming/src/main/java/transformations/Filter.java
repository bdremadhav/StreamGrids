package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import util.WrapperMessage;

import java.util.*;

/**
 * Created by cloudera on 5/21/17.
 */
public class Filter implements Transformation {
    @Override
    public JavaDStream<WrapperMessage> transform(JavaRDD emptyRDD,Map<Integer, JavaDStream<WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside filter prevPid = " + prevPid);
        JavaDStream prevDStream = prevDStreamMap.get(prevPid);
        JavaDStream filteredDStream = null;

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        final String check = filterProperties.getProperty("operator");
        ;
        final String filterValue = filterProperties.getProperty("filtervalue");
        final String colName = filterProperties.getProperty("column");

        System.out.println("operator = " + check);
        System.out.println("filtervalue = " + filterValue);
        System.out.println("colName = " + colName);

        JavaDStream<WrapperMessage> finalDStream = prevDStream.transform(new Function<JavaRDD<WrapperMessage>, JavaRDD<WrapperMessage>>() {
            @Override
            public JavaRDD<WrapperMessage> call(JavaRDD<WrapperMessage> rddWrapperMessage) throws Exception {

                JavaRDD<Row> rddRow = rddWrapperMessage.map(new Function<WrapperMessage, Row>() {
                                                                @Override
                                                                public Row call(WrapperMessage wrapperMessage) throws Exception {
                                                                    return wrapperMessage.getRow();
                                                                }
                                                            }
                );


                SQLContext sqlContext = SQLContext.getOrCreate(rddWrapperMessage.context());

                DataFrame dataFrame = sqlContext.createDataFrame(rddRow, schema);

                DataFrame filteredDF = null;


                if (dataFrame != null && !dataFrame.rdd().isEmpty()) {
                    if (check.equals("equals")) {
                        System.out.println("showing dataframe before filter ");
                        dataFrame.show(100);
                        filteredDF = dataFrame.filter(dataFrame.col(colName).equalTo(filterValue));
                        filteredDF.show(100);
                        System.out.println("showing dataframe after filter ");
                    } else {
                        System.out.println("showing dataframe before filter ");
                        dataFrame.show(100);
                        filteredDF = dataFrame.filter(dataFrame.col(colName).gt(filterValue));
                        filteredDF.show(100);
                        System.out.println("showing dataframe after filter ");
                    }
                }
                JavaRDD<WrapperMessage> finalRDD = emptyRDD;
                if (filteredDF != null) {
                    finalRDD = filteredDF.javaRDD().map(new Function<Row, WrapperMessage>() {
                                                            @Override
                                                            public WrapperMessage call(Row row) throws Exception {
                                                                return new WrapperMessage(row);
                                                            }
                                                        }
                    );

                }
                    return finalRDD;
                    // return filteredDF.javaRDD();
                }
        });
        return finalDStream;
    }
}