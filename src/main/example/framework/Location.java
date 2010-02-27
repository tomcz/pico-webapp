package example.framework;

public class Location {

    private final String url;

    private boolean contextRelative = true;
    private boolean servletRelative = true;

    public Location(String url) {
        this.url = url;
    }

    public Location(Class handlerType) {
        this(handlerType, new PathVariables());
    }

    public Location(Class handlerType, String paramName, Object paramValue) {
        this(handlerType, new PathVariables().set(paramName, paramValue.toString()));
    }

    public Location(Class handlerType, PathVariables pathVariables) {
        this(URIPatternFactory.expand(handlerType, pathVariables));
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
