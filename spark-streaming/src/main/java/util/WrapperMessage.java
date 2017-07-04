package util;

import org.apache.spark.sql.Row;

import java.io.Serializable;

public class WrapperMessage implements Serializable{
    Row row;
    public WrapperMessage(Row row){
        this.row=row;
    }
    public Row getRow() {
        return row;
    }
    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "WrapperMessage{" +
                "row=" + row +
                '}';
    }
}