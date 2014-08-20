package app.component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MongoWriter implements Runnable {

    @Autowired
    private DBCollection collection;

    @Override
    public void run() {

          Random randomGenerator = new Random();

          DBObject document = new BasicDBObject();
          document.put("value", String.valueOf(randomGenerator.nextLong()));
          document.put("ts", System.currentTimeMillis());

          collection.save(document);
    }
}
