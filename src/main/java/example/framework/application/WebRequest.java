package example.framework.application;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import example.framework.Identity;
import example.framework.IdentityFactory;
import example.framework.PathVariables;
import example.framework.Request;
import example.framework.RequestContext;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static org.apache.commons.lang3.StringUtils.defaultString;

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

    public Optional<Cookie> getCookie(final String name) {
        return from(request.getCookies()).firstMatch(new Predicate<Cookie>() {
            public boolean apply(Cookie cookie) {
                return cookie.getName().equals(name);
            }
        });
    }

    public Optional<String> getCookieValue(String name) {
        return getCookie(name).transform(new Function<Cookie, String>() {
            public String apply(Cookie cookie) {
                return cookie.getValue();
            }
        });
    }

    public String getRequestBodyText() {
        return request.getRequestBodyText();
    }

    public InputStream getRequestBodyStream() {
        return request.getRequestBodyStream();
    }
}
