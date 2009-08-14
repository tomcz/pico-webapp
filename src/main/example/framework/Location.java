package example.framework;

import example.utils.Maps;

import java.util.Collections;
import java.util.Map;

public class Location {

    private final String url;

    private boolean contextRelative = true;
    private boolean servletRelative = true;

    public Location(String url) {
        this.url = url;
    }

    public Location(Class handlerType) {
        this(handlerType, Collections.<String, String>emptyMap());
    }

    public Location(Class handlerType, String paramName, Object paramValue) {
        this(handlerType, Maps.create(paramName, paramValue.toString()));
    }

    public Location(Class handlerType, Map<String, String> pathVariables) {
        this(URITemplateFactory.createFrom(handlerType).expand(pathVariables));
    }

    public String getUrl() {
        return url;
    }

    public boolean isContextRelative() {
        return contextRelative;
    }

    public void setContextRelative(boolean contextRelative) {
        this.contextRelative = contextRelative;
    }

    public boolean isServletRelative() {
        return servletRelative;
    }

    public void setServletRelative(boolean servletRelative) {
        this.servletRelative = servletRelative;
    }
}
