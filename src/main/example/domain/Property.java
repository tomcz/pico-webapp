package example.domain;

import example.utils.Strings;
import org.apache.commons.lang3.StringUtils;

public class Property {

    private final String value;
    private final String message;

    public Property() {
        this("");
    }

    public Property(String value) {
        this(value, "");
    }

    public Property(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public Property copyWithMessage(String newMessage) {
        return new Property(value, newMessage);
    }

    public boolean isValid() {
        return StringUtils.isEmpty(message);
    }

    @Override
    public String toString() {
        return Strings.toString(this);
    }
}
