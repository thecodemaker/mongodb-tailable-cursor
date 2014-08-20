package app.component;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoReader implements Runnable {

    @Autowired
    private DBCollection collection;


    public static final int BATCH_SIZE = 10;

    @Override
    public void run() {

           DBCursor cursor = collection.find()
                .sort(new BasicDBObject("$natural", 1))
                .batchSize(BATCH_SIZE)
                .addOption(Bytes.QUERYOPTION_TAILABLE)
                .addOption(Bytes.QUERYOPTION_AWAITDATA);

        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            System.out.println(object);
        }

    }
}
