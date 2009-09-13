package example.framework;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class MethodNotAllowedResponse implements Response {

    private Set<RequestMethod> allowed;

    public MethodNotAllowedResponse(Set<RequestMethod> allowed) {
        this.allowed = allowed;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Allow", StringUtils.join(allowed, ","));
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
