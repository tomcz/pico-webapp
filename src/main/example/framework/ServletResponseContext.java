package example.framework;

import example.utils.Maps;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ServletResponseContext implements ResponseContext {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ServletResponseContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String getContextPath() {
        return request.getContextPath();
    }

    public String getServletPath() {
        return request.getServletPath();
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = Maps.create();
        Enumeration names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return Collections.unmodifiableMap(attributes);
    }

    public void setHeader(Header header) {
        addCookies(header.getCookies());
        addFields(header.getFields());
    }

    private void addCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }

    private void addFields(Map<String, List<String>> fields) {
        for (String name : fields.keySet()) {
            boolean isFirst = true;
            for (String value : fields.get(name)) {
                if (isFirst) {
                    response.setHeader(name, value);
                } else {
                    response.addHeader(name, value);
                }
                isFirst = false;
            }
        }
    }

    public void sendError(int errorCode) throws IOException {
        response.sendError(errorCode);
    }

    public void sendError(int errorCode, String message) throws IOException {
        response.sendError(errorCode, message);
    }

    public void sendRedirect(String url) throws IOException {
        response.sendRedirect(url);
    }

    public void setStatusCode(int code) {
        response.setStatus(code);
    }

    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    public void setCharacterEncoding(String charset) {
        response.setCharacterEncoding(charset);
    }

    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    public OutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }
}
