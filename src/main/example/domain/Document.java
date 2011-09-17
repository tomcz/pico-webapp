package example.domain;

import example.framework.Identity;
import example.utils.Strings;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.Matchers.equalTo;

public class Document {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public enum Field {
        one, two, date
    }

    private Identity id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Map<Field, Property> properties;

    private String version;

    public Document() {
        this(new Identity());
    }

    public Document(Identity id) {
        this(id, new LocalDateTime());
    }

    public Document(Identity id, LocalDateTime created) {
        LocalDateTime now = new LocalDateTime();
        this.properties = newHashMap();
        this.createdAt = created;
        this.updatedAt = now;
        this.id = id;
    }

    public Identity getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt.toString(DATE_FORMAT);
    }

    public LocalDateTime getCreated() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt.toString(DATE_FORMAT);
    }

    public void setUpdated(LocalDateTime updated) {
        this.updatedAt = updated;
    }

    public LocalDateTime getUpdated() {
        return updatedAt;
    }

    public Property get(Field field) {
        Property property = properties.get(field);
        return (property != null) ? property : new Property();
    }

    public void set(Field field, Property property) {
        properties.put(field, property);
        updatedAt = new LocalDateTime();
    }

    public List<Field> getFields() {
        return Arrays.asList(Field.values());
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isValid() {
        return select(properties.values(), having(on(Property.class).isValid(), equalTo(false))).isEmpty();
    }

    @Override
    public String toString() {
        return Strings.toString(this);
    }
}
