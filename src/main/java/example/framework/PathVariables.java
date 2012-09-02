package example.framework;

import org.weborganic.furi.Parameters;
import org.weborganic.furi.URIParameters;

public class PathVariables {

    private final URIParameters params = new URIParameters();

    public PathVariables set(String key, String value) {
        params.set(key, value);
        return this;
    }

    public String get(String key) {
        return params.getValue(key);
    }

    public Parameters getParameters() {
        return params;
    }
}
