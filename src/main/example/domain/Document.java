package example.domain;

import example.utils.Maps;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Document {

    public enum Field {
        one, two, date
    }

    private Identity identity;
    private LocalDateTime created;
    private Map<Field, Property> properties;

    public Document() {
        this(new Identity());
    }

    public Document(Identity identity) {
        this.identity = identity;
        this.properties = Maps.create();
        this.created = new LocalDateTime();
    }

    public Identity getIdentity() {
        return identity;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Property get(Field field) {
        Property property = properties.get(field);
        return (property != null) ? property : new Property();
    }

    public void set(Field field, Property property) {
        properties.put(field, property);
    }

    public List<Field> getFields() {
        return Arrays.asList(Field.values());
    }

    public boolean isValid() {
        for (Property property : properties.values()) {
            if (!property.isValid()) {
                return false;
            }
        }
        return true;
    }
}
