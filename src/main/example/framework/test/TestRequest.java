package example.framework.test;

import example.framework.Identity;
import example.framework.Request;
import example.framework.application.CookieMatcher;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static example.utils.GenericCollections.newArrayList;
import static example.utils.GenericCollections.newHashMap;

public class TestRequest implements Request {

    private Map<String, List<String>> parameters = newHashMap();
    private Map<String, String> pathVariables = newHashMap();
    private Map<String, Identity> identities = newHashMap();
    private List<Cookie> cookies = newArrayList();
    private InputStream requestBodyStream;
    private String requestBodyText;

    public String getParameter(String name) {
        List<String> params = getParameters(name);
        return CollectionUtils.isEmpty(params) ? null : params.get(0);
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
        return (Cookie) CollectionUtils.find(cookies, new CookieMatcher(name));
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
        parameters.put(name, Arrays.asList(values));
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
