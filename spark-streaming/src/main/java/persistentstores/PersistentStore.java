package persistentstores;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;

/**
 * Created by cloudera on 6/8/17.
 */
public interface PersistentStore {
    public void persist(JavaDStream<Row> df, Integer pid, Integer prevPid, StructType schema) throws Exception;
}
