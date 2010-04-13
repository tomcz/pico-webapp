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

    public enum Field {
        one, two, date
    }

    private Identity identity;
    private LocalDateTime created;
    private Map<Field, Property> properties;

    private String version;

    public Document() {
        this(new Identity());
    }

    public Document(Identity identity) {
        this(identity, new LocalDateTime());
    }

    public Document(Identity identity, LocalDateTime created) {
        this.properties = newHashMap();
        this.identity = identity;
        this.created = created;
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
