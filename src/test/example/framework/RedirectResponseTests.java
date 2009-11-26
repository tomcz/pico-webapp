package example.framework;

import org.junit.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;

import java.io.IOException;

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
