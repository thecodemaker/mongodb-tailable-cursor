package app;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;

public class CollectionReader implements Runnable {

    private DBCollection collection;

    public static final int BATCH_SIZE = 10;

    @Autowired
    public CollectionReader(DB db) {
        this.collection = db.getCollection("cappedCollection");
    }

    @Override
    public void run() {

        DBCursor cursor = collection.find()
                .batchSize(BATCH_SIZE)
                .addOption(Bytes.QUERYOPTION_TAILABLE)
                .addOption(Bytes.QUERYOPTION_AWAITDATA);

        while (cursor.hasNext()) {
            DBObject object = cursor.next();
        }

    }
}
