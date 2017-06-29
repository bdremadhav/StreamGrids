package transformations;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;

import java.util.Map;
import java.util.Set;

/**
 * Created by cloudera on 5/21/17.
 */
public interface Transformation {
    public JavaDStream<Row> transform(Map<Integer,JavaDStream<Row>> prevDStreamMap, Map<Integer,Set<Integer>> prevMap, Integer pid, StructType schema);
}