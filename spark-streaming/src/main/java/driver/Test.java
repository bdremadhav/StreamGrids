package driver;

import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by cloudera on 6/27/17.
 */
public class Test {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Log Analyzer");
        conf.setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(10000));
        Map<String,String> kafkaParams = new HashMap<String,String>();
        kafkaParams.put("bootstrap.servers","localhost:9092");
        kafkaParams.put("zookeeper.connect","localhost:2181");
        Set<String> topics = new HashSet<>();
        topics.add("test");
        JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
        JavaDStream finalStream = directKafkaStream.map(Tuple2::_2);
        finalStream.transform(new Function<JavaRDD<String>, JavaRDD<String>>() {
            @Override
            public JavaRDD<String> call(JavaRDD<String> stringJavaRDD) throws Exception {
                stringJavaRDD.take(10);
                return null;
            }
        });
        finalStream.print();
        ssc.start();
        ssc.awaitTermination();
    }
}
