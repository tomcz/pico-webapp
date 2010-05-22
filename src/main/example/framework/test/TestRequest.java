package example.framework.test;

import example.framework.Identity;
import example.framework.Request;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static example.utils.Generics.first;
import static example.utils.Generics.newArrayList;
import static example.utils.Generics.newHashMap;
import static org.hamcrest.Matchers.equalTo;

public class TestRequest implements Request {

    private Map<String, List<String>> parameters = newHashMap();
    private Map<String, String> pathVariables = newHashMap();
    private Map<String, Identity> identities = newHashMap();
    private List<Cookie> cookies = newArrayList();
    private InputStream requestBodyStream;
    private String requestBodyText;

    public String getParameter(String name) {
        return first(getParameters(name));
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
        return selectFirst(cookies, having(on(Cookie.class).getName(), equalTo(name)));
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
