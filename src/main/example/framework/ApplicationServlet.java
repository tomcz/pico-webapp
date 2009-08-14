package example.framework;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationServlet extends HttpServlet {

    private Application application;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        application = (Application) servletConfig.getServletContext().getAttribute(Application.class.getName());
        super.init(servletConfig);
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {

        RequestMethod method = RequestMethod.valueOf(servletRequest.getMethod());
        Response response = application.process(servletRequest, method);
        response.render(servletRequest, servletResponse);
    }
}
