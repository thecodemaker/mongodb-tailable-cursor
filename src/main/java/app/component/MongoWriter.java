package app.component;

import app.domain.Document;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MongoWriter implements Runnable {

    @Autowired
    private DBCollection collection;

    private AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void run() {

        Random randomGenerator = new Random();
        while (running.get()) {

            Document document = new Document(String.valueOf(randomGenerator.nextLong()));

            collection.save(document);

            threadBabySleep();
        }
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
