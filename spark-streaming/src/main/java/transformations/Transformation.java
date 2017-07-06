package transformations;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import util.WrapperMessage;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Created by cloudera on 5/21/17.
 */
public interface Transformation extends Serializable{
    public JavaPairDStream<String,WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer,JavaPairDStream<String,WrapperMessage>> prevDStreamMap, Map<Integer,Set<Integer>> prevMap, Integer pid, StructType schema);
}