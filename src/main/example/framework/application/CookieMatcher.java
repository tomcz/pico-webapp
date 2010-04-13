package example.framework.application;

import org.apache.commons.collections.Predicate;

import javax.servlet.http.Cookie;

public class CookieMatcher implements Predicate {

    private final String name;

    public CookieMatcher(String name) {
        this.name = name;
    }

    public boolean evaluate(Object object) {
        return ((Cookie) object).getName().equals(name);
    }
}
