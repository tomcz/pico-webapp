package example.framework;

import example.utils.Function;
import example.utils.Lists;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MethodNotAllowedResponse implements Response {

    private Set<RequestMethod> allowed;

    public MethodNotAllowedResponse(Set<RequestMethod> allowed) {
        this.allowed = allowed;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> methods = allowedMethods();
        Collections.sort(methods);

        response.setHeader("Allow", StringUtils.join(methods, ","));
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private List<String> allowedMethods() {
        return Lists.map(allowed, new Function<RequestMethod, String>() {
            public String execute(RequestMethod item) {
                return item.name();
            }
        });
    }
}
