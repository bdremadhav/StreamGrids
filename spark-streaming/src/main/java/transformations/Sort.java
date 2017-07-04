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
 * Created by cloudera on 6/9/17.
 */
public class Sort implements Transformation {
    @Override
    public JavaDStream<WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaDStream<WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside filter prevPid = " + prevPid);
        JavaDStream prevDStream = prevDStreamMap.get(prevPid);
        JavaDStream filteredDStream = null;

        GetProperties getProperties = new GetProperties();
        Properties sortProperties=  getProperties.getProperties(String.valueOf(pid),"default");

        final String colName=sortProperties.getProperty("column");
        final String order = sortProperties.getProperty("order");
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
                DataFrame prevDataFrame = sqlContext.createDataFrame(rddRow, schema);
                DataFrame sortedDF = null;


                if(prevDataFrame!=null && !prevDataFrame.rdd().isEmpty()){

                    System.out.println("showing dataframe before sort ");
                    prevDataFrame.show(100);
                    if(order.equalsIgnoreCase("descending")) {
                        sortedDF = prevDataFrame.sort(prevDataFrame.col(colName).desc());
                    }else{
                        sortedDF = prevDataFrame.sort(prevDataFrame.col(colName).asc());
                    }
                    sortedDF.show(100);
                    System.out.println("showing dataframe after sort ");

                }

                JavaRDD<WrapperMessage> finalRDD = emptyRDD;
                if (sortedDF != null) {
                    finalRDD = sortedDF.javaRDD().map(new Function<Row, WrapperMessage>() {
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
   /* //@Override
    public DataFrame transform(Map<Integer, DataFrame> prevDataFrameMap, Map<Integer, Set<Integer>> prevMap, Integer pid) {

        DataFrame prevDataFrame = prevDataFrameMap.get(prevPid);
        DataFrame sortedDF =null;
        GetProperties getProperties=new GetProperties();

        Properties sortProperties=  getProperties.getProperties(String.valueOf(pid),"default");
        //String check="";

        String colName=new String();
        String order = new String();

        order = sortProperties.getProperty("order");
        colName = sortProperties.getProperty("column");
        System.out.println("colName = " + colName);

        if(prevDataFrame!=null && !prevDataFrame.rdd().isEmpty()){

                System.out.println("showing dataframe before sort ");
                prevDataFrame.show(100);
            if(order.equalsIgnoreCase("descending")) {
                sortedDF = prevDataFrame.sort(prevDataFrame.col(colName).desc());
            }else{
                sortedDF = prevDataFrame.sort(prevDataFrame.col(colName).asc());
            }
                sortedDF.show(100);
                System.out.println("showing dataframe after sort ");

        }

        return sortedDF;
    }

    @Override
    public JavaDStream<Row> transform(Map<Integer, JavaDStream<Row>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        return null;
    }*/
}
