package example.framework;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WebRequest implements Request {

    private final HttpServletRequest request;
    private final Map<String, String> pathVariables;

    public WebRequest(HttpServletRequest request, Map<String, String> pathVariables) {
        this.request = request;
        this.pathVariables = pathVariables;
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
}
