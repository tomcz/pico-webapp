package example.framework.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.StringUtils.defaultIfEmpty;

public class VersionFilter implements Filter {

    private static final String VERSION = "version";

    private String name;
    private String version;

    public void init(FilterConfig filterConfig) throws ServletException {
        name = getClass().getName() + "." + filterConfig.getFilterName();
        version = filterConfig.getInitParameter(VERSION);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        addVersionAttribute(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addVersionAttribute(ServletRequest servletRequest) {
        if (servletRequest.getAttribute(name) == null) {
            servletRequest.setAttribute(name, Boolean.TRUE);
            servletRequest.setAttribute(VERSION, defaultIfEmpty(version, randomAlphanumeric(7)));
        }
    }
}
