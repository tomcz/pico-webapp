package example.framework.test;

import example.framework.Location;
import example.framework.RequestContext;
import example.framework.RequestMethod;
import example.utils.Lists;
import example.utils.Maps;

import javax.servlet.http.Cookie;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestRequestContext implements RequestContext {

    private final RequestMethod method;
    private final String lookupPath;

    private final Map<String, List<String>> parameters = Maps.create();
    private final List<Cookie> cookies = Lists.create();

    public TestRequestContext(RequestMethod method, Location location) {
        this.lookupPath = location.getUrl();
        this.method = method;
    }

    public String getLookupPath() {
        return lookupPath;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getParameter(String name) {
        return Lists.first(getParameterValues(name), "");
    }

    public void setParameter(String name, String... values) {
        parameters.put(name, Lists.create(values));
    }

    public List<String> getParameterValues(String name) {
        List<String> values = parameters.get(name);
        if (values != null) {
            return Collections.unmodifiableList(values);
        }
        return Collections.emptyList();
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
