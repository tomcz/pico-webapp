package example.framework;

import example.utils.Lists;
import example.utils.Maps;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Header {

    private List<Cookie> cookies = Lists.create();
    private Map<String, List<String>> fields = Maps.create();

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        addCookie(cookie);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setField(String name, String... values) {
        fields.put(name, Arrays.asList(values));
    }

    public Map<String, List<String>> getFields() {
        return fields;
    }
}
