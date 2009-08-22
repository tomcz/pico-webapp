package example.domain.services;

import example.domain.Document;
import example.domain.Document.Field;
import example.domain.Identity;
import example.domain.Property;
import example.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CouchdbJson {

    private final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");

    public List<Identity> parseIdentities(String response) {
        try {
            JSONObject root = new JSONObject(response);
            JSONArray rows = root.getJSONArray("rows");

            List<Identity> result = Lists.create();
            for (int i = 0; i < rows.length(); i++) {
                JSONObject entry = rows.getJSONObject(i);
                result.add(Identity.fromValue(entry.getString("id")));
            }
            return result;

        } catch (JSONException e) {
            throw new UnhandledException(e);
        }
    }

    public String marshall(Document doc) {
        try {
            List<JSONObject> properties = Lists.create();
            for (Field field : doc.getFields()) {
                Property property = doc.get(field);
                JSONObject entry = new JSONObject();
                entry.put("field", field.name());
                entry.put("value", property.getValue());
                entry.put("message", property.getMessage());
                properties.add(entry);
            }

            JSONObject root = new JSONObject();
            root.put("created", format.print(doc.getCreated()));
            root.put("properties", properties);

            if (StringUtils.isNotEmpty(doc.getVersion())) {
                root.put("_rev", doc.getVersion());
            }

            return root.toString();

        } catch (JSONException e) {
            throw new UnhandledException(e);
        }
    }

    public Document unmarshall(Identity identity, String response) {
        try {
            JSONObject root = new JSONObject(response);
            DateTime created = format.parseDateTime(root.getString("created"));

            Document doc = new Document(identity, new LocalDateTime(created));
            doc.setVersion(root.getString("_rev"));

            JSONArray properties = root.getJSONArray("properties");
            for (int i = 0; i < properties.length(); i++) {
                JSONObject entry = properties.getJSONObject(i);
                Field field = Field.valueOf(entry.getString("field"));
                String value = entry.getString("value");
                String message = entry.getString("message");
                doc.set(field, new Property(value, message));
            }

            return doc;

        } catch (JSONException e) {
            throw new UnhandledException(e);
        }
    }

    public void updateVersion(Document doc, String response) {
        try {
            JSONObject root = new JSONObject(response);
            String version = root.optString("_rev");
            if (StringUtils.isNotEmpty(version)) {
                doc.setVersion(version);
            }
        } catch (JSONException e) {
            throw new UnhandledException(e);
        }
    }
}
