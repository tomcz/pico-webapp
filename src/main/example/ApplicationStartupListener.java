package example;

import example.domain.error.BadComponent;
import example.domain.services.ServicesComponent;
import example.domain.web.WebComponent;
import example.framework.Application;
import example.framework.Component;
import example.framework.Configuration;
import example.framework.application.DefaultConfiguration;
import example.framework.application.WebApplication;
import example.framework.application.error.ErrorComponent;
import example.framework.application.route.RoutingComponent;
import example.framework.identity.IdentityFactoryComponent;
import example.framework.template.TemplateComponent;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

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
        Configuration configuration = parseContextParameters(context);

        List<Component> components = newArrayList();
        components.add(new IdentityFactoryComponent());
        components.add(new RoutingComponent());
        components.add(new TemplateComponent());
        components.add(new ServicesComponent());
        components.add(new ErrorComponent());
        components.add(new BadComponent());
        components.add(new WebComponent());

        return new WebApplication(components, configuration, context);
    }

    private Configuration parseContextParameters(ServletContext context) {
        Map<String, String> props = newHashMap();
        Enumeration names = context.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            props.put(name, context.getInitParameter(name));
        }
        return new DefaultConfiguration(props);
    }
}
