package example.framework.servlet;

import example.framework.Application;
import example.framework.Response;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.StringUtils.defaultString;

public class ApplicationServlet extends HttpServlet {

    private Application application;
    private String version;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        ServletContext context = servletConfig.getServletContext();
        application = (Application) context.getAttribute(Application.class.getName());

        String showVersion = servletConfig.getInitParameter("version-tag");
        version = "per-request".equals(showVersion) ? null : showVersion;
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {

        servletRequest.setAttribute("version", defaultString(version, randomAlphanumeric(7)));
        Response response = application.process(new ServletRequestContext(servletRequest));
        response.render(new ServletResponseContext(servletRequest, servletResponse));
    }
}
