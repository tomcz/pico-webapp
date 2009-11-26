package example.framework;

import example.utils.Maps;

import java.util.Map;

public class PathVariables {

    private final Map<String, String> vars = Maps.create();

    public PathVariables set(String key, String value) {
        vars.put(key, value);
        return this;
    }

    public String get(String key) {
        return vars.get(key);
    }

    public int size() {
        return vars.size();
    }
}
