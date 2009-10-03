package example.framework;

import java.util.Map;

public class DefaultConfiguration implements Configuration {

    private final Map<String, String> properties;

    public DefaultConfiguration(Map<String, String> properties) {
        this.properties = properties;
    }

    public String get(String key) {
        return properties.get(key);
    }
}
