package example.framework.application.route;

import example.framework.Redirect;
import example.framework.ResponseContext;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;

public class RedirectResponseTests {

    @Test
    public void shouldCreateRelativePathForRedirect() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        given(context.getContextPath()).willReturn("/context");
        given(context.getServletPath()).willReturn("/servlet");

        RedirectResponse response = new RedirectResponse(new Redirect("/foo"));
        response.render(context);

        verify(context).sendRedirect("/context/servlet/foo");
    }

    @Test
    public void shouldAddHeaderToResponse() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        Redirect redirect = new Redirect("/foo");

        RedirectResponse response = new RedirectResponse(redirect);
        response.render(context);

        verify(context).setHeader(redirect);
    }
}
