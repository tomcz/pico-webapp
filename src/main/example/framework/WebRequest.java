package example.framework;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WebRequest implements Request {

    private final HttpServletRequest request;
    private final PathVariables pathVariables;
    private final IdentityFactory identityFactory;

    public WebRequest(HttpServletRequest request, IdentityFactory identityFactory, PathVariables pathVariables) {
        this.identityFactory = identityFactory;
        this.pathVariables = pathVariables;
        this.request = request;
    }

    public String getParameter(String name) {
        return StringUtils.defaultString(request.getParameter(name));
    }

    public List<String> getParameters(String name) {
        String[] values = request.getParameterValues(name);
        return (values != null) ? Arrays.asList(values) : Collections.<String>emptyList();
    }

    public String getPathVariable(String name) {
        return StringUtils.defaultString(pathVariables.get(name));
    }

    public Identity getIdentity(String name) {
        return identityFactory.createFrom(getPathVariable(name));
    }

    public Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return (cookie != null) ? cookie.getValue() : "";
    }
}
