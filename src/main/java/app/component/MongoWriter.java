package app.component;

import app.domain.Document;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MongoWriter extends AbstractMongoRunner {

    @Autowired
    private DBCollection collection;

    @Override
    public void run() {

        Random randomGenerator = new Random();
        while (isRunning()) {

            Document document = new Document(String.valueOf(randomGenerator.nextLong()));

            collection.save(document);

            threadBabySleep();
        }
    }
}
