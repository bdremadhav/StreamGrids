package transformations;

import com.wipro.ats.bdre.md.api.GetProperties;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import util.WrapperMessage;

import java.util.*;

/**
 * Created by cloudera on 7/3/17.
 */
public class Window implements Transformation{

    @Override
    public JavaDStream<WrapperMessage> transform(JavaRDD emptyRDD, Map<Integer, JavaDStream<WrapperMessage>> prevDStreamMap, Map<Integer, Set<Integer>> prevMap, Integer pid, StructType schema) {
        List<Integer> prevPidList = new ArrayList<>();
        prevPidList.addAll(prevMap.get(pid));
        Integer prevPid = prevPidList.get(0);
        System.out.println("Inside Window prevPid = " + prevPid);
        JavaDStream prevDStream = prevDStreamMap.get(prevPid);

        GetProperties getProperties = new GetProperties();
        Properties filterProperties = getProperties.getProperties(String.valueOf(pid), "default");
        String windowType = filterProperties.getProperty("window-type");
        String windowDurationString = filterProperties.getProperty("window-duration");
        String slideDurationString = filterProperties.getProperty("slide-duration");

        //TODO Comment these 3 lines once UI is done
        windowType = "SlidingWindow";
        windowDurationString = "60000";
        slideDurationString = "60000";

        Duration windowDuration = new Duration(Long.parseLong(windowDurationString));
        JavaDStream windowDStream = null;
        if(windowType.equalsIgnoreCase("FixedWindow")){
            windowDStream = prevDStream.window(windowDuration);
            System.out.println(" Inside FixedWindow" );
            windowDStream.print();
            windowDStream.foreachRDD(new Function() {
                @Override
                public Object call(Object o) throws Exception {
                    System.out.println(" for each rdd in window " );
                    return null;
                }
            });
        }
        else {
            Duration slideDuration = new Duration(Long.parseLong(slideDurationString));
            windowDStream = prevDStream.window(windowDuration,slideDuration);
            System.out.println(" Inside Sliding Window" );
            windowDStream.print();
        }
        return windowDStream;

    }
}
