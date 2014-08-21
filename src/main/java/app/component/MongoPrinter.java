package app.component;

import app.domain.Document;
import org.springframework.stereotype.Component;

@Component
public class MongoPrinter {

    public void print(Document document) {
        System.out.println("MongoReader sent document" + document);
    }

}
