package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import com.wipro.ats.bdre.md.beans.GetPropertiesInfo;
import messageformat.Parser;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.dstream.DStream;
import scala.Function1;
import scala.Function2;
import scala.tools.cmd.gen.AnyVals;

import java.sql.Wrapper;
import java.util.*;

/**
 * Created by cloudera on 5/21/17.
 */
public class Filter implements Transformation {
    private static int getIndexInRow(StructType schema, String colName) {
        String[] colNames = schema.fieldNames();
        Map<String, String> result = new HashMap<String, String>();
        int index = -1;
        for (int i = 0; i < colNames.length; i++) {
            if (colNames[i].equals(colName)) {
                index = i;
            }
        }

        return index;
    }

    public JavaDStream<WrapperMessage> convertToWrapperDStream(Map<Integer, JavaDStream> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside filter prevPid = " + prevPid);
        JavaDStream prevDStream = prevDStreamMap.get(prevPid);
        JavaDStream filteredDStream = null;

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        final String check = filterProperties.getProperty("operator");

        final String filterValue = filterProperties.getProperty("filtervalue");
        final String colName = filterProperties.getProperty("column");

        System.out.println("operator = " + check);

        System.out.println("filtervalue = " + filterValue);
        //colName = filterProperties.getProperty("column");
        System.out.println("colName = " + colName);

        JavaDStream<WrapperMessage> prevDStream1 = prevDStream.map(new Function<String, WrapperMessage>() {
            @Override
            public WrapperMessage call(String record) throws Exception {
                Object[] attributes = new Object[]{};
                attributes = Parser.parseMessage(record, pid);
                return new WrapperMessage(RowFactory.create(attributes));
            }
        });
        return prevDStream1;
    }

    @Override
    public JavaDStream<WrapperMessage> transform(Map<Integer, JavaDStream> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        //TODO: fetch the filter logic from DB

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
        //colName = filterProperties.getProperty("column");
        System.out.println("colName = " + colName);


        JavaDStream<WrapperMessage> prevDStream1 = convertToWrapperDStream(prevDStreamMap, prevMap, pid, schema);

        JavaDStream<WrapperMessage> finalDStream = prevDStream1.transform(new Function<JavaRDD<WrapperMessage>, JavaRDD<WrapperMessage>>() {
            @Override
            public JavaRDD<WrapperMessage> call(JavaRDD<WrapperMessage> rdd) throws Exception {
                JavaRDD<WrapperMessage> rowRDD = rdd.map(new Function<WrapperMessage, WrapperMessage>() {
                                                             @Override
                                                             public WrapperMessage call(WrapperMessage record) throws Exception {
                                                                 try {
                                                                     Object[] attributes = new Object[]{};
                                                                     attributes = Parser.parseMessage(record.getRecord(), pid);
                                                                     return new WrapperMessage(RowFactory.create(attributes));
                                                                 } catch (Exception e) {
                                                                     e.printStackTrace();
                                                                     throw e;
                                                                 }
                                                             }
                                                         }
                );

                JavaRDD<Row> rowSchemaRDD = rdd.map(new Function<WrapperMessage, Row>() {
                                                        @Override
                                                        public Row call(WrapperMessage rdd) throws Exception {
                                                            return new Row();
                                                        }
                                                    }
                );


                SQLContext sqlContext = SQLContext.getOrCreate(rdd.context());

                DataFrame dataFrame = sqlContext.createDataFrame(rowSchemaRDD, schema);

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

                filteredDF.printSchema();
                JavaRDD<WrapperMessage> finalRDD = filteredDF.javaRDD().map(new Function<Row, WrapperMessage>() {
                                                                                @Override
                                                                                public WrapperMessage call(Row row) throws Exception {
                                                                                    return new WrapperMessage(row);
                                                                                }
                                                                            }
                );


                return finalRDD;
                // return filteredDF.javaRDD();
            }
        });

        finalDStream.window()
        return finalDStream;


    }
}