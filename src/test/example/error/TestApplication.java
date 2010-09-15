package example.error;

import example.framework.Application;
import example.framework.Component;
import example.framework.InputStreamSource;
import example.framework.Response;
import example.framework.application.WebApplication;
import example.framework.application.route.RoutingComponent;
import example.framework.identity.IdentityFactoryComponent;
import example.framework.template.TemplateComponent;
import example.framework.test.TestInputStreamSource;
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
        InputStreamSource source = new TestInputStreamSource();
        List<Component> components = newArrayList();

        components.add(new IdentityFactoryComponent());
        components.add(new TemplateComponent());
        components.add(new RoutingComponent());
        components.add(new ErrorComponent());

        return new WebApplication(components, source);
    }
}
