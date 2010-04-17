package example.domain;

import example.framework.Identity;
import org.hamcrest.Matcher;
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static example.utils.GenericCollections.newHashMap;
import static example.utils.PredicateMatcher.with;
import static org.apache.commons.collections.CollectionUtils.countMatches;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class Document {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public enum Field {
        one, two, date;

    }

    private Identity identity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Map<Field, Property> properties;

    private String version;

    public Document() {
        this(new Identity());
    }

    public Document(Identity identity) {
        this(identity, new LocalDateTime());
    }

    public Document(Identity identity, LocalDateTime created) {
        LocalDateTime now = new LocalDateTime();
        this.properties = newHashMap();
        this.identity = identity;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Identity getIdentity() {
        return identity;
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
        Matcher<Property> validProperty = hasProperty("valid", equalTo(true));
        return countMatches(properties.values(), with(validProperty)) == properties.size();
    }
}
