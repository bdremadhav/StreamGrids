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
    @Override
    public JavaDStream<Row> transform(Map<Integer, JavaDStream<Row>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
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


        JavaDStream<Row> finalDStream = prevDStream.transform(new Function<JavaRDD<Row>, JavaRDD<Row>>() {
            @Override
            public JavaRDD<Row> call(JavaRDD<Row> rowSchemaRDD) throws Exception {

                SQLContext sqlContext = SQLContext.getOrCreate(rowSchemaRDD.context());
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

                //filteredDF.printSchema();
                return filteredDF.javaRDD();

            }
        });
        return finalDStream;
    }
}