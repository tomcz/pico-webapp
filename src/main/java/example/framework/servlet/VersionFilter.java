package example.framework.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class VersionFilter implements Filter {

    private String name;
    private String version;

    public void init(FilterConfig filterConfig) throws ServletException {
        name = getClass().getName() + "." + filterConfig.getFilterName();
        version = defaultIfEmpty(filterConfig.getInitParameter("version"), randomAlphanumeric(7));
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        addVersionAttribute((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addVersionAttribute(HttpServletRequest request) {
        if (request.getAttribute(name) == null) {
            request.setAttribute("servletPath", request.getContextPath() + request.getServletPath());
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("version", version);
            request.setAttribute(name, Boolean.TRUE);
        }
    }
}
