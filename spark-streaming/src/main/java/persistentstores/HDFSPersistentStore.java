package persistentstores;

import com.wipro.ats.bdre.md.api.GetProperties;
import com.wipro.ats.bdre.md.beans.GetPropertiesInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.catalyst.plans.logical.Except;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by cloudera on 5/21/17.
 */
public class HDFSPersistentStore implements PersistentStore {

    @Override
    public void persist(JavaDStream<Row> dStream, Integer pid, Integer prevPid, StructType schema) throws Exception {
        try {
            final String hdfsPath = "/user/cloudera/spark-streaming-data/";
            System.out.println("Inside emitter hdfs, persisting pid = " + prevPid);
            GetProperties getProperties = new GetProperties();


            Properties hdfsProperties = getProperties.getProperties(String.valueOf(pid), "kafka");

            Long date = new Date().getTime();
            JavaDStream<Row> finaldstream =  dStream.transform(new Function<JavaRDD<Row>,JavaRDD<Row>>() {
                @Override
                public JavaRDD<Row> call(JavaRDD<Row> rowJavaRDD) throws Exception {
                    SQLContext sqlContext = SQLContext.getOrCreate(rowJavaRDD.context());
                    DataFrame df = sqlContext.createDataFrame(rowJavaRDD, schema);
                    if (df != null && !df.rdd().isEmpty()) {
                        System.out.println("showing dataframe df before writing to hdfs  ");
                        df.show(100);
                        System.out.println("df.rdd().count() = " + df.rdd().count());
                        String inputPathName = hdfsPath + date + "_" + pid + "/";
                        String finalOutputPathName = hdfsPath + date + "-" + pid + "/";
                        df.rdd().saveAsTextFile(inputPathName);
                        System.out.println("showing dataframe df after writing to hdfs  ");
                        df.show(100);

                      /*  Path inputPath = new Path(inputPathName);
                        Path finalOutputPath = new Path(finalOutputPathName);
                        System.out.println("finalOutputPath = " + finalOutputPath);

                        Configuration configuration = new Configuration();
                        FileSystem fileSystem = inputPath.getFileSystem(configuration);
                        boolean result = FileUtil.copyMerge(fileSystem, inputPath, fileSystem, finalOutputPath, true, configuration, null);
                        System.out.println("merged result = " + result);*/

                    }

                    return rowJavaRDD;
                }
            });
            finaldstream.foreachRDD(new Function<JavaRDD<Row>, Void>() {
                @Override
                public Void call(JavaRDD<Row> rowJavaRDD) throws Exception {
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