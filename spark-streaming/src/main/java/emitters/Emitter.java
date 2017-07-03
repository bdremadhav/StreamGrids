package emitters;

import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.api.java.JavaDStream;

import java.io.Serializable;

/**
 * Created by cloudera on 6/8/17.
 */
public interface Emitter extends Serializable{
    public void persist(JavaDStream df, Integer pid, Integer prevPid,StructType schema) throws Exception;
}
