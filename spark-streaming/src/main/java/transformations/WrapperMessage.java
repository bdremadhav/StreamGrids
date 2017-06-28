package transformations;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;

import java.util.Objects;

class WrapperMessage{
    Row row;
    StructType schema;
    Objects[] attributes;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    String record;

    public WrapperMessage(Row row){
        this.row=row;
    }
    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public StructType getSchema() {
        return schema;
    }

    public void setSchema(StructType schema) {
        this.schema = schema;
    }

    public Objects[] getAttributes() {
        return attributes;
    }

    public void setAttributes(Objects[] attributes) {
        this.attributes = attributes;
    }
}