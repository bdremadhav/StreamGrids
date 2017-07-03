package persistentstores;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import util.WrapperMessage;

import java.util.Date;
import java.util.Properties;

/**
 * Created by cloudera on 5/21/17.
 */
public class HDFSPersistentStore implements PersistentStore {

    @Override
    public void persist(JavaRDD emptyRDD, JavaDStream<WrapperMessage> dStream, Integer pid, Integer prevPid, StructType schema) throws Exception {
        try {
            final String hdfsPath = "/user/cloudera/spark-streaming-data/";
            System.out.println("Inside emitter hdfs, persisting pid = " + prevPid);
            GetProperties getProperties = new GetProperties();


            Properties hdfsProperties = getProperties.getProperties(String.valueOf(pid), "kafka");

            Long date = new Date().getTime();
            JavaDStream<WrapperMessage> finalDStream =  dStream.transform(new Function<JavaRDD<WrapperMessage>,JavaRDD<WrapperMessage>>() {
                @Override
                public JavaRDD<WrapperMessage> call(JavaRDD<WrapperMessage> wrapperMessageJavaRDD) throws Exception {
                    JavaRDD<Row> rowJavaRDD = wrapperMessageJavaRDD.map(new Function<WrapperMessage, Row>() {
                                                                    @Override
                                                                    public Row call(WrapperMessage wrapperMessage) throws Exception {
                                                                        return wrapperMessage.getRow();
                                                                    }
                                                                }
                    );
                    SQLContext sqlContext = SQLContext.getOrCreate(rowJavaRDD.context());
                    DataFrame df = sqlContext.createDataFrame(rowJavaRDD, schema);
                    if (df != null && !df.rdd().isEmpty()) {
                        System.out.println("showing dataframe df before writing to hdfs  ");
                        df.show(100);
                        System.out.println("df.rdd().count() = " + df.rdd().count());
                        String inputPathName = hdfsPath + date + "_" + pid + "/";
                        String finalOutputPathName = hdfsPath + date + "-" + pid + "/";
                        //df.rdd().saveAsTextFile(inputPathName);
                        df.rdd().take(10);
                        System.out.println("showing dataframe df after writing to hdfs  ");
                        df.show(100);

                    }
                    JavaRDD<WrapperMessage> finalRDD = emptyRDD;
                    if (df != null) {
                        finalRDD = df.javaRDD().map(new Function<Row, WrapperMessage>() {
                                                        @Override
                                                        public WrapperMessage call(Row row) throws Exception {
                                                            return new WrapperMessage(row);
                                                        }
                                                    }
                        );
                    }
                    return finalRDD;
                }
            });
            finalDStream.foreachRDD(new Function<JavaRDD<WrapperMessage>, Void>() {
                @Override
                public Void call(JavaRDD<WrapperMessage> rowJavaRDD) throws Exception {
                    return null;
                }
            });
           // dStream.dstream().saveAsTextFiles(hdfsPath,date.toString());


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}