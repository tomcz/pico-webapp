package example.framework;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        return StringUtils.defaultString(request.getParameter(name));
    }

    public List<String> getParameterValues(String name) {
        String[] values = request.getParameterValues(name);
        if (values != null) {
            return Arrays.asList(values);
        }
        return Collections.emptyList();
    }

    public List<Cookie> getCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.asList(cookies);
        }
        return Collections.emptyList();
    }
}
