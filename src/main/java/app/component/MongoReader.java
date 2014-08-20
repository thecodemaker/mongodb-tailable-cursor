package app.component;

import app.domain.Document;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MongoReader implements Runnable {

    @Autowired
    private DBCollection collection;

    private AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void run() {

        long lastTimeStamp = 0;

        while(running.get()) {

            DBCursor cursor = createTailableCursor(lastTimeStamp);

            while (cursor.hasNext() && running.get()) {

                Document document = (Document) cursor.next();

                lastTimeStamp = document.getLong("ts");

                threadBabySleep();
            }
        }
    }

    private DBCursor createTailableCursor(long lastTimeStamp) {

        DBObject query = new BasicDBObject("ts", new BasicDBObject("$gt", lastTimeStamp));

        collection.setObjectClass(Document.class);
        return collection.find(query)
                .sort(new BasicDBObject("$natural", 1))
                        //    .batchSize(BATCH_SIZE)
                .addOption(Bytes.QUERYOPTION_TAILABLE)
                .addOption(Bytes.QUERYOPTION_AWAITDATA);

    }

    public void setRunning(boolean running) {
        setRunning(new AtomicBoolean(running));
    }

    public void setRunning(AtomicBoolean running) {
        this.running = running;
    }

    protected void threadBabySleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
