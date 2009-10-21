package example.framework;

import java.util.Map;

public class Redirect {

    private Cookies cookies;
    private Headers headers;

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

    public Redirect(Class handlerType, Map<String, String> pathVariables) {
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

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
}
