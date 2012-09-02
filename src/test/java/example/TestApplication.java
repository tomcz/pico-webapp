package example;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import example.domain.error.BadComponent;
import example.domain.services.ServicesComponent;
import example.domain.web.WebComponent;
import example.framework.Application;
import example.framework.Component;
import example.framework.Response;
import example.framework.application.WebApplication;
import example.framework.application.error.ErrorComponent;
import example.framework.application.route.RoutingComponent;
import example.framework.identity.IdentityFactoryComponent;
import example.framework.template.TemplateFactory;
import example.framework.test.TestFreemarkerTemplateFactory;
import example.framework.test.TestRequestContext;
import example.framework.test.TestResponseContext;

import java.util.List;
import java.util.Map;

public class TestApplication {

    private Application application;

    public TestApplication() {
        application = createApplication();
    }

    private static Application createApplication() {
        TemplateFactory templateFactory = new TestFreemarkerTemplateFactory();
        List<Component> components = Lists.newArrayList();

        components.add(new IdentityFactoryComponent());
        components.add(new RoutingComponent());
        components.add(new ServicesComponent());
        components.add(new ErrorComponent());
        components.add(new BadComponent());
        components.add(new WebComponent());

        return new WebApplication(components, templateFactory);
    }

    public TestResponseContext process(TestRequestContext request) throws Exception {
        Response response = application.process(request);
        TestResponseContext context = responseContext();
        response.render(context);
        return context;
    }

    private TestResponseContext responseContext() {
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("contextPath", "");
        attributes.put("servletPath", "");
        attributes.put("version", "");

        TestResponseContext context = new TestResponseContext();
        context.setAttributes(attributes);
        return context;
    }

    public void shutdown() {
        try {
            application.dispose();
        } catch (Exception e) {
            // ignore
        }
    }

    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                shutdown();
            }
        }));
    }
}
