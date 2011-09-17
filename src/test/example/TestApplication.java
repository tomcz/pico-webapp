package example;

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

import static example.utils.Generics.newArrayList;

public class TestApplication {

    private Application application;

    public TestApplication() {
        application = createApplication();
    }

    public TestResponseContext process(TestRequestContext request) throws Exception {
        Response response = application.process(request);
        TestResponseContext context = new TestResponseContext();
        response.render(context);
        return context;
    }

    private static Application createApplication() {
        TemplateFactory templateFactory = new TestFreemarkerTemplateFactory();
        List<Component> components = newArrayList();

        components.add(new IdentityFactoryComponent());
        components.add(new RoutingComponent());
        components.add(new ErrorComponent());

        return new WebApplication(components, templateFactory);
    }
}
