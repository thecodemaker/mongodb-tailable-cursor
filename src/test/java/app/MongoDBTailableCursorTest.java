package app;

import app.component.MongoReader;
import app.component.MongoWriter;
import app.config.ApplicationConfig;
import app.domain.Document;
import com.mongodb.DBCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
public class MongoDBTailableCursorTest {

    @Autowired
    private MongoWriter writer;

    @Autowired
    private MongoReader reader;

    @Autowired
    private DBCollection collection;

    @Test
    public void testWriter() throws Exception {

        Thread writeThread = new Thread(writer);
        writeThread.start();

        waitForSomeTime();

        writer.setRunning(false);

        waitForSomeTime();

        writeThread.interrupt();

        collection.setObjectClass(Document.class);
        Document document = (Document) collection.findOne();
        assertThat(document, is(notNullValue()));
    }

    @Test
    public void testReader() throws Exception {

        collection.insert(new Document("A"));
        collection.insert(new Document("B"));

        Thread readThread = new Thread(reader);
        readThread.start();

        waitForSomeTime();waitForSomeTime();waitForSomeTime();waitForSomeTime();waitForSomeTime();

        reader.setRunning(false);

        waitForSomeTime();

        readThread.interrupt();

    }

    private void waitForSomeTime() throws InterruptedException {
        Thread.sleep(5000);
    }
}
