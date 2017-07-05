package persistentstores;

import org.apache.avro.data.Json;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.spark.JavaHBaseContext;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import scala.Tuple2;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.util.function.BiFunction;
/*
 * Created by cloudera on 5/21/17.
 */

public class HBasePersistentStore implements PersistentStore{

    @Override
    public void persist(DataFrame df, Integer pid, Integer prevPid) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        HBaseConfiguration hconfig = new HBaseConfiguration(new Configuration());
        HTableDescriptor htable = new HTableDescriptor(TableName.valueOf("User"));
        htable.addFamily( new HColumnDescriptor("Id"));
        htable.addFamily( new HColumnDescriptor("Name"));
        System.out.println( "Connecting..." );
       // HBaseAdmin hbase_admin = new HBaseAdmin( hconfig );
        System.out.println( "Creating Table..." );
       // hbase_admin.createTable( htable ); System.out.println("Done!");

    }


}

