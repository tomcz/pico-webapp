package example.framework.servlet;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import example.framework.PathHelper;
import example.framework.RequestContext;
import example.framework.RequestMethod;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
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
        BufferedReader input = null;
        try {
            input = request.getReader();
            return CharStreams.toString(input);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            Closeables.closeQuietly(input);
        }
    }

    public InputStream getRequestBodyStream() {
        try {
            return request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
