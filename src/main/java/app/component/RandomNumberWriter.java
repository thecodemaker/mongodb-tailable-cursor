package app.component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberWriter implements Writer {

    @Autowired
    private DBCollection collection;

    public static final int SLEEP_MILLISECONDS = 10000;

    @Override
    public void write() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Random randomGenerator = new Random();
                while (true) {

                    BasicDBObject object = new BasicDBObject();
                    object.put("value", String.valueOf(randomGenerator.nextLong()));

                    collection.save(object);

                    try {
                        Thread.sleep(SLEEP_MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();
    }
}
