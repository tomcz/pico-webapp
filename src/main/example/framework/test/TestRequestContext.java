package example.framework.test;

import example.framework.Location;
import example.framework.RequestContext;
import example.framework.RequestMethod;
import example.utils.Lists;
import example.utils.Maps;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestRequestContext implements RequestContext {

    private final RequestMethod method;
    private final String lookupPath;

    private final Map<String, List<String>> parameters = Maps.create();
    private final List<Cookie> cookies = Lists.create();

    private String requestBodyText;
    private InputStream requestBodyStream;

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
        return Collections.unmodifiableList(cookies);
    }

    public void setRequestBodyText(String requestBodyText) {
        this.requestBodyText = requestBodyText;
    }

    public String getRequestBodyText() {
        return requestBodyText;
    }

    public void setRequestBodyStream(InputStream requestBodyStream) {
        this.requestBodyStream = requestBodyStream;
    }

    public InputStream getRequestBodyStream() {
        return requestBodyStream;
    }
}
