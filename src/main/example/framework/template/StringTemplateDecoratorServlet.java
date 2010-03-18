package example.framework.template;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import example.framework.PathHelper;
import example.framework.servlet.ServletResponseContext;
import example.framework.servlet.ServletWebRoot;
import org.apache.commons.io.FilenameUtils;

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
        factory = new WebTemplateFactory(new ServletWebRoot(context), "/decorators");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lookupPath = pathHelper.getLookupPathForRequest(request);
        String templateName = FilenameUtils.getBaseName(lookupPath);
        Template template = factory.create(templateName);

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

        TemplateResponse view = new TemplateResponse(template);
        view.render(new ServletResponseContext(request, response));
    }
}
