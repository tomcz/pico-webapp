package example.framework.servlet;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import example.framework.PathHelper;
import example.framework.template.Template;
import example.framework.template.TemplateFactory;
import example.framework.template.WebTemplateFactory;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StringTemplateDecoratorServlet extends HttpServlet {

    private final PathHelper pathHelper = new PathHelper();

    private TemplateFactory factory;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        ServletContext context = servletConfig.getServletContext();
        factory = new WebTemplateFactory(new ServletInputStreamSource(context), "");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        Template template = factory.create(StringUtils.substringBeforeLast(lookupPath, ".st"));

        HTMLPage htmlPage = (HTMLPage) request.getAttribute(RequestConstants.PAGE);

        if (htmlPage == null) {
            template.set("title", "No Title");
            template.set("body", "No Body");
            template.set("head", "<!-- No head -->");
        } else {
            template.set("page", htmlPage);
            template.set("title", htmlPage.getTitle());
            template.set("head", htmlPage.getHead());
            template.set("body", htmlPage.getBody());
        }

        template.render(new ServletResponseContext(request, response));
    }
}
