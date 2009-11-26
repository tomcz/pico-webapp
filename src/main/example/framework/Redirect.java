package example.framework;

public class Redirect extends Header {

    private final Location location;

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
        return location.isContextRelative();
    }

    public boolean isServletRelative() {
        return location.isServletRelative();
    }
}
