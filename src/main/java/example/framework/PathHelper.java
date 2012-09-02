package example.framework;

import javax.servlet.http.HttpServletRequest;

public class PathHelper {

    public String getLookupPathForRequest(HttpServletRequest request) {
        // First, see if it is an included request
        String includeServletPath = (String) request.getAttribute("javax.servlet.include.servlet_path");
        if (includeServletPath != null) {
            // Try path info; only if that's null (servlet is mapped to an
            // URL extension instead of to prefix) use servlet path.
            String includePathInfo = (String) request.getAttribute("javax.servlet.include.path_info");
            return (includePathInfo == null) ? includeServletPath : includePathInfo;
        }
        // Seems that the servlet was not called as the result of a
        // RequestDispatcher.include(...). Try pathInfo then servletPath again,
        // only now directly on the request object:
        String path = request.getPathInfo();
        if (path != null) {
            return path;
        }
        path = request.getServletPath();
        if (path != null) {
            return path;
        }
        // Seems that it is a servlet mapped with prefix, and there was no extra path info.
        return "";
    }
}
