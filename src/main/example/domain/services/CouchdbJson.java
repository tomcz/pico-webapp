package example.domain.services;

import example.domain.Document;
import example.domain.Document.Field;
import example.domain.Property;
import example.framework.Identity;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONValue;

import java.util.List;
import java.util.Map;

import static example.utils.Generics.newArrayList;
import static example.utils.Generics.newHashMap;

@SuppressWarnings({"unchecked"})
public class CouchdbJson {

    private final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public String marshall(Document doc) {
        List<Map<String, String>> properties = newArrayList();

        for (Field field : doc.getFields()) {
            Property property = doc.get(field);
            Map<String, String> entry = newHashMap();
            entry.put("field", field.name());
            entry.put("value", property.getValue());
            entry.put("message", property.getMessage());
            properties.add(entry);
        }

        Map<String, Object> root = newHashMap();
        root.put("created", format.print(doc.getCreated()));
        root.put("updated", format.print(doc.getUpdated()));
        root.put("properties", properties);

        if (StringUtils.isNotEmpty(doc.getVersion())) {
            root.put("_rev", doc.getVersion());
        }

        return JSONValue.toJSONString(root);
    }

    public Document unmarshall(Identity identity, String response) {
        Map<String, Object> root = (Map) JSONValue.parse(response);
        return parseDocument(identity, root);
    }

    public void updateVersion(Document doc, String response) {
        Map<String, String> root = (Map) JSONValue.parse(response);
        String version = root.get("_rev");
        if (StringUtils.isNotEmpty(version)) {
            doc.setVersion(version);
        }
    }

    public List<Document> unmarshallDocs(String response) {
        Map<String, Object> root = (Map) JSONValue.parse(response);
        List<Map<String, Object>> rows = (List) root.get("rows");

        List<Document> result = newArrayList();
        for (Map<String, Object> row : rows) {
            Identity identity = Identity.fromValue((String) row.get("id"));
            Map<String, Object> doc = (Map) row.get("doc");
            result.add(parseDocument(identity, doc));
        }
        return result;
    }

    private Document parseDocument(Identity identity, Map<String, Object> root) {
        DateTime created = format.parseDateTime((String) root.get("created"));
        DateTime updated = format.parseDateTime((String) root.get("updated"));

        Document doc = new Document(identity, new LocalDateTime(created));
        doc.setUpdated(new LocalDateTime(updated));
        doc.setVersion((String) root.get("_rev"));

        List<Map<String, String>> properties = (List) root.get("properties");
        for (Map<String, String> property : properties) {
            Field field = Field.valueOf(property.get("field"));
            String value = property.get("value");
            String message = property.get("message");
            doc.set(field, new Property(value, message));
        }
        return doc;
    }
}
