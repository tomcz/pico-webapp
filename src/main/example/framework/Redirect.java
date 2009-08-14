package example.framework;

import example.utils.Lists;

import javax.servlet.http.Cookie;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Redirect {

    private final Location location;

    private List<Cookie> cookies = null;

    public Redirect(String url) {
        location = new Location(url);
    }

    public Redirect(Class handlerType) {
        location = new Location(handlerType);
    }

    public Redirect(Class handlerType, String paramName, Object paramValue) {
        location = new Location(handlerType, paramName, paramValue);
    }

    public Redirect(Class handlerType, Map<String, String> pathVariables) {
        location = new Location(handlerType, pathVariables);
    }

    public String getUrl() {
        return location.getUrl();
    }

    public boolean isContextRelative() {
        return location.isContextRelative();
    }

    public void setContextRelative(boolean contextRelative) {
        location.setContextRelative(contextRelative);
    }

    public boolean isServletRelative() {
        return location.isServletRelative();
    }

    public void setServletRelative(boolean servletRelative) {
        location.setServletRelative(servletRelative);
    }

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        addCookie(cookie);
    }

    public void addCookie(Cookie cookie) {
        if (cookies == null) {
            cookies = Lists.create();
        }
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        if (cookies == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(cookies);
    }
}
