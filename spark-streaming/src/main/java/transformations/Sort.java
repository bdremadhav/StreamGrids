package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;

import java.util.*;

/**
 * Created by cloudera on 6/9/17.
 */
public class Sort implements Transformation {
    //@Override
    public DataFrame transform(Map<Integer, DataFrame> prevDataFrameMap, Map<Integer, Set<Integer>> prevMap, Integer pid) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Sort prevPid = " + prevPid);
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
    }
}
