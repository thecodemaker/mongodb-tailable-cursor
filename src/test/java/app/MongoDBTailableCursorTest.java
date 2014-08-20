package app;

import app.component.Writer;
import app.config.ApplicationConfig;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfig.class})
public class MongoDBTailableCursorTest {

    @Autowired
    private Writer writer;

    @Autowired
    private DBCollection collection;

    @Test
    public void testWriter() throws Exception {
       writer.write();

        DBObject object = collection.findOne();
        assertThat(object, is(notNullValue()));
    }
}
