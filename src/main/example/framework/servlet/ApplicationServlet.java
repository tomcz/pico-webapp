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

public class ApplicationServlet extends HttpServlet {

    private Application application;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        ServletContext context = servletConfig.getServletContext();
        application = (Application) context.getAttribute(Application.class.getName());
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {

        Response response = application.process(new ServletRequestContext(servletRequest));
        response.render(new ServletResponseContext(servletRequest, servletResponse));
    }
}
