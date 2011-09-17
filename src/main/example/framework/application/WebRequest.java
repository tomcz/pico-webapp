package example.framework.application;

import example.framework.Identity;
import example.framework.IdentityFactory;
import example.framework.PathVariables;
import example.framework.Request;
import example.framework.RequestContext;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.hamcrest.Matchers.equalTo;

public class WebRequest implements Request {

    private final RequestContext request;
    private final PathVariables pathVariables;
    private final IdentityFactory identityFactory;

    public WebRequest(RequestContext request, IdentityFactory identityFactory, PathVariables pathVariables) {
        this.identityFactory = identityFactory;
        this.pathVariables = pathVariables;
        this.request = request;
    }

    public String getParameter(String name) {
        return request.getParameter(name);
    }

    public List<String> getParameters(String name) {
        return request.getParameterValues(name);
    }

    public String getPathVariable(String name) {
        return defaultString(pathVariables.get(name));
    }

    public Identity getIdentity(String name) {
        return identityFactory.createFrom(getPathVariable(name));
    }

    public Cookie getCookie(String name) {
        return selectFirst(request.getCookies(), having(on(Cookie.class).getName(), equalTo(name)));
    }

    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return (cookie != null) ? cookie.getValue() : "";
    }

    public String getRequestBodyText() {
        return request.getRequestBodyText();
    }

    public InputStream getRequestBodyStream() {
        return request.getRequestBodyStream();
    }
}
