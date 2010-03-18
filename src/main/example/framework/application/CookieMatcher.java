package example.framework.application;

import example.utils.Matcher;

import javax.servlet.http.Cookie;

public class CookieMatcher implements Matcher<Cookie> {

    private final String name;

    public CookieMatcher(String name) {
        this.name = name;
    }

    public boolean matches(Cookie item) {
        return item.getName().equals(name);
    }
}
