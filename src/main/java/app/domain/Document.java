package app.domain;

import com.mongodb.BasicDBObject;

public class Document extends BasicDBObject{

    public Document() { }

    public Document(String value) {
        this(value, System.currentTimeMillis());
    }

    public Document(String value, long ts) {
        this.put("value", value);
        this.put("ts", ts);
    }

    public String get_id() {
        return this.get("_id").toString();
    }
}
