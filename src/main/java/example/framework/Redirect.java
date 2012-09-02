package example.framework;

public class Redirect extends Header {

    private final Location location;

    private boolean contextRelative = true;
    private boolean servletRelative = true;

    public Redirect(Location location) {
        this.location = location;
    }

    public Redirect(String url) {
        this(new Location(url));
    }

    public Redirect(Class handlerType) {
        this(new Location(handlerType));
    }

    public Redirect(Class handlerType, String paramName, Object paramValue) {
        this(new Location(handlerType, paramName, paramValue));
    }

    public Redirect(Class handlerType, PathVariables pathVariables) {
        this(new Location(handlerType, pathVariables));
    }

    public String getUrl() {
        return location.getUrl();
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
