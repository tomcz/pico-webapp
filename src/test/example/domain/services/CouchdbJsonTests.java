package example.domain.services;

import example.domain.Document;
import example.domain.Property;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CouchdbJsonTests {

    @Test
    public void shouldPassRoundTripEncodeAndDecode() {
        Document doc = new Document();
        doc.set(Document.Field.one, new Property("hello", "there"));

        CouchdbJson converter = new CouchdbJson();
        String text = converter.marshall(doc);

        Document result = converter.unmarshall(doc.getIdentity(), text);
        assertThat(result.get(Document.Field.one).getValue(), equalTo("hello"));
        assertThat(result.get(Document.Field.one).getMessage(), equalTo("there"));
    }
}
