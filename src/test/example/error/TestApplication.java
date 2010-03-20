package example.error;

import example.framework.Application;
import example.framework.Component;
import example.framework.Response;
import example.framework.application.WebApplication;
import example.framework.application.route.Routes;
import example.framework.container.PicoContainer;
import example.framework.identity.IdentityFactoryComponent;
import example.framework.template.TemplateComponent;
import example.framework.test.TestRequestContext;
import example.framework.test.TestResponseContext;
import example.framework.test.TestWebRoot;
import example.utils.Lists;
import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.util.List;

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
        PicoContainer container = new PicoContainer();
        container.registerInstance(new TestWebRoot(new File(SystemUtils.getUserDir(), "web")));

        List<Component> components = Lists.create();
        components.add(new IdentityFactoryComponent());
        components.add(new TemplateComponent());
        components.add(new ErrorComponent());

        return new WebApplication(container, new Routes(), components);
    }
}
