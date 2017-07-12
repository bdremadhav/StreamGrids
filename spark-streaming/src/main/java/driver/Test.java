package driver;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

public class Test {
    public static void main(String[] args) throws Exception {
// create connection with HBase
       /* Configuration config = null;
        try {
            config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "127.0.0.1");
            config.set("hbase.zookeeper.property.clientPort", "2181");
            config.set("hbase.master", "127.0.0.1:60000");
            HBaseAdmin.checkHBaseAvailable(config);
            System.out.println("HBase is running!");
        } catch (MasterNotRunningException e) {
            System.out.println("HBase is not running!");
            System.exit(1);
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        config.set(TableInputFormat.INPUT_TABLE, "tableName");
        // new Hadoop API configuration
        Job newAPIJobConfiguration1 = Job.getInstance(config);
        newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "tableName");
        newAPIJobConfiguration1.setOutputFormatClass(org.apache.hadoop.hbase.mapreduce.TableOutputFormat.class);
        // create Key, Value pair to store in HBase
        JavaPairRDD<ImmutableBytesWritable, Put> hbasePuts = javaRDD.mapToPair(new PairFunction<Row, ImmutableBytesWritable, Put>() {
            @Override
            public Tuple2<ImmutableBytesWritable, Put> call(Row row) throws Exception {
                Put put = new Put(Bytes.toBytes(row.getString(0)));
                put.add(Bytes.toBytes("columFamily"), Bytes.toBytes("columnQualifier1"), Bytes.toBytes(row.getString(1)));
                put.add(Bytes.toBytes("columFamily"), Bytes.toBytes("columnQualifier2"), Bytes.toBytes(row.getString(2)));
                return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(), put);
            }
        });
        // save to HBase- Spark built-in API method
        hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration());*/

    }


}