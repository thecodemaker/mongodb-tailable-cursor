package app.domain;

import com.mongodb.BasicDBObject;

public class Document extends BasicDBObject{

    private String _id;
    private String value;
    private long ts;

    public Document() {

    }

    public Document(String value) {
        this(value, System.currentTimeMillis());
    }

    public Document(String value, long ts) {
        this.put("value", value);
        this.put("ts", ts);
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String get_id() {
        return _id;
    }

    public String getValue() {
        return value;
    }

    public long getTs() {
        return ts;
    }
}
