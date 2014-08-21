package app.component;

import app.domain.Document;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MongoReader extends AbstractMongoRunner {

    @Autowired
    private DBCollection collection;

    @Autowired
    private MongoPrinter printer;

    private AtomicLong lastTimestamp = new AtomicLong(0);

    @Override
    public void run() {

        DBCursor cursor = createTailableCursor(lastTimestamp.get());

        while (cursor.hasNext() && isRunning()) {

            Document document = (Document) cursor.next();

            printer.print(document);

            lastTimestamp = new AtomicLong(document.getLong("ts"));

            threadBabySleep();
        }

        cursor.close();
    }

    private DBCursor createTailableCursor(long lastTimeStamp) {

        DBObject query = new BasicDBObject("ts", new BasicDBObject("$gt", lastTimeStamp));
        DBObject sort = new BasicDBObject("$natural", 1);

        collection.setObjectClass(Document.class);
        return collection.find(query)
                .sort(sort)
                .addOption(Bytes.QUERYOPTION_TAILABLE)
                .addOption(Bytes.QUERYOPTION_AWAITDATA);
    }

    public long getLastTimestamp() {
        return lastTimestamp.get();
    }

    public void setLastTimestamp(AtomicLong lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
}
