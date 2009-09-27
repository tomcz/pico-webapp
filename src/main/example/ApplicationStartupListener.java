package example;

import example.domain.services.ServicesComponent;
import example.domain.web.WebComponent;
import example.error.ErrorComponent;
import example.framework.Application;
import example.framework.Component;
import example.framework.WebApplication;
import example.framework.identity.IdentityFactoryComponent;
import example.framework.template.TemplateComponent;
import example.utils.Lists;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

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
        Properties configuration = parseContextParameters(context);

        List<Component> components = Lists.create();

        components.add(new IdentityFactoryComponent());
        components.add(new TemplateComponent());
        components.add(new ServicesComponent());
        components.add(new ErrorComponent());
        components.add(new WebComponent());

        return new WebApplication(components, context, configuration);
    }

    private Properties parseContextParameters(ServletContext context) {
        Properties props = new Properties();
        Enumeration names = context.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            props.setProperty(name, context.getInitParameter(name));
        }
        return props;
    }
}
