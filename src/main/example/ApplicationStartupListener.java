package example;

import example.domain.services.ServicesComponent;
import example.framework.Application;
import example.framework.Component;
import example.framework.WebApplication;
import example.framework.template.TemplateComponent;
import example.utils.Lists;
import example.web.common.WebComponent;
import example.web.form.FormComponent;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ApplicationStartupListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute(Application.class.getName(), createApplication(context));
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        Application application = (Application) context.getAttribute(Application.class.getName());
        if (application != null) {
            application.dispose();
        }
    }

    private Application createApplication(ServletContext context) {
        List<Component> components = Lists.create();

        components.add(new TemplateComponent());
        components.add(new ServicesComponent());
        components.add(new WebComponent());
        components.add(new FormComponent());

        return new WebApplication(components, context);
    }
}
