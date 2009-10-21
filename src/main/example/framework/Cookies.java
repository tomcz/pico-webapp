package example.framework;

import example.utils.Lists;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Cookies {

    private List<Cookie> cookies = Lists.create();

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        addCookie(cookie);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addTo(HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }
}
