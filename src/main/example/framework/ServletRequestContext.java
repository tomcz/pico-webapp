package example.framework;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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

    public String getRequestBodyText() {
        ServletInputStream input = null;
        try {
            String encoding = StringUtils.defaultIfEmpty(request.getCharacterEncoding(), "UTF-8");
            input = request.getInputStream();

            return IOUtils.toString(input, encoding);

        } catch (IOException e) {
            throw new UnhandledException(e);

        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public InputStream getRequestBodyStream() {
        try {
            return request.getInputStream();
        } catch (IOException e) {
            throw new UnhandledException(e);
        }
    }
}
