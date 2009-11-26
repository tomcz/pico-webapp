package example.framework;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletRequestContext implements RequestContext {

    private final PathHelper pathHelper = new PathHelper();

    private final HttpServletRequest request;

    public ServletRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    public String getLookupPath() {
        return pathHelper.getLookupPathForRequest(request);
    }

    public RequestMethod getMethod() {
        return RequestMethod.valueOf(request.getMethod());
    }

    public String getParameter(String name) {
        return request.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    public Cookie[] getCookies() {
        return request.getCookies();
    }
}
