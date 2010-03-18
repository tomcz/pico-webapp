package example.error;

import example.framework.Application;
import example.framework.Component;
import example.framework.Location;
import example.framework.RequestMethod;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.text.pattern.PatternMatcher.matchesPattern;
import static org.hamcrest.text.pattern.Patterns.anyCharacterIn;
import static org.hamcrest.text.pattern.Patterns.exactly;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.junit.Assert.assertThat;

public class ErrorHandlingIntegrationTests {

    @Test
    public void shouldHandleApplicationErrorsByRedirectingUserToErrorReferencePresenter() throws IOException {
        Application application = createTestApplication();

        Location location = new Location(BadPresener.class);
        Response response = application.process(new TestRequestContext(RequestMethod.GET, location));

        TestResponseContext context = new TestResponseContext();
        response.render(context);

        String redirectedUrl = context.getRedirectURL();
        assertThat(redirectedUrl, matchesPattern(sequence("/error/", exactly(7, anyCharacterIn("A-Z0-9")))));

        String errorRef = StringUtils.substringAfterLast(redirectedUrl, "/");

        response = application.process(new TestRequestContext(RequestMethod.GET, new Location(redirectedUrl)));

        context = new TestResponseContext();
        response.render(context);

        assertThat(context.getResponseBodyText(), containsString(errorRef));
    }

    private Application createTestApplication() {
        PicoContainer container = new PicoContainer();
        container.registerInstance(new TestWebRoot(new File(SystemUtils.getUserDir(), "web")));

        List<Component> components = Lists.create();
        components.add(new IdentityFactoryComponent());
        components.add(new TemplateComponent());
        components.add(new ErrorComponent());

        return new WebApplication(container, new Routes(), components);
    }
}
