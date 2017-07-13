package driver;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import scala.Tuple2;
import util.WrapperMessage;

import java.io.Serializable;

/**
 * Created by cloudera on 6/27/17.
 */
public class MapFunction implements Function,Serializable {

    @Override
    public Object call(Object inputRecord) throws Exception {
        Tuple2<String,WrapperMessage> input = (Tuple2<String,WrapperMessage>) inputRecord;

        Row inputRow = input._2.getRow();
        int noOfElements = inputRow.size();
        String[] attributes = new String[noOfElements];
        for(int i=0; i<noOfElements; i++){
            attributes[i] = inputRow.getString(i);
            if(i == 0){
                attributes[i] = inputRow.getString(i) + "madhav";
            }

        }
        Row outputRow = RowFactory.create(attributes);
        return new WrapperMessage(outputRow);
    }
}
