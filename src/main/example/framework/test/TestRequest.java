package example.framework.test;

import example.framework.Identity;
import example.framework.Request;
import example.framework.application.CookieMatcher;
import example.utils.Lists;
import example.utils.Maps;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestRequest implements Request {

    private Map<String, List<String>> parameters = Maps.create();
    private Map<String, String> pathVariables = Maps.create();
    private Map<String, Identity> identities = Maps.create();
    private List<Cookie> cookies = Lists.create();
    private InputStream requestBodyStream;
    private String requestBodyText;

    public String getParameter(String name) {
        return Lists.first(getParameters(name));
    }

    public List<String> getParameters(String name) {
        return parameters.get(name);
    }

    public String getPathVariable(String name) {
        return pathVariables.get(name);
    }

    public Identity getIdentity(String name) {
        return identities.get(name);
    }

    public Cookie getCookie(String name) {
        return Lists.find(cookies, new CookieMatcher(name));
    }

    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return (cookie != null) ? cookie.getValue() : null;
    }

    public String getRequestBodyText() {
        return requestBodyText;
    }

    public InputStream getRequestBodyStream() {
        return requestBodyStream;
    }

    public void setParameter(String name, String... values) {
        parameters.put(name, Lists.create(values));
    }

    public void setPathVariable(String name, String value) {
        pathVariables.put(name, value);
    }

    public void setIdentity(String name, Identity identity) {
        identities.put(name, identity);
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void setRequestBodyStream(InputStream requestBodyStream) {
        this.requestBodyStream = requestBodyStream;
    }

    public void setRequestBodyText(String requestBodyText) {
        this.requestBodyText = requestBodyText;
    }
}
