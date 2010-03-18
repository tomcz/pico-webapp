package example.domain;

import example.domain.Document.Field;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DocumentTests {

    @Test
    public void blankDocumentIsValid() throws Exception {
        assertTrue(new Document().isValid());
    }

    @Test
    public void documentIsValidWhenAllPropertiesAreValid() throws Exception {
        Document doc = new Document();
        doc.set(Field.one, new Property("hello"));
        doc.set(Field.two, new Property("there"));
        assertTrue(doc.isValid());
    }

    @Test
    public void documentIsNotValidWhenOneFieldIsNotValid() throws Exception {
        Document doc = new Document();
        doc.set(Field.one, new Property("hello"));
        doc.set(Field.two, new Property("there", "error"));
        assertFalse(doc.isValid());
    }
}
