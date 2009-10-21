package example.framework;

import org.junit.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectResponseTests {

    @Test
    public void shouldCreateRelativePathForRedirect() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        given(servletRequest.getContextPath()).willReturn("/context");
        given(servletRequest.getServletPath()).willReturn("/servlet");

        RedirectResponse response = new RedirectResponse(new Redirect("/foo"));
        response.render(servletRequest, servletResponse);

        verify(servletResponse).sendRedirect("/context/servlet/foo");
    }

    @Test
    public void shouldAddHeadersToResponse() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        HeaderFields headers = new HeaderFields();
        headers.set("foo", "bar");

        Redirect redirect = new Redirect("/foo");
        redirect.setFields(headers);

        RedirectResponse response = new RedirectResponse(redirect);
        response.render(servletRequest, servletResponse);

        verify(servletResponse).setHeader("foo", "bar");
    }

    @Test
    public void shouldAddCookiesToResponse() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        Cookie cookie = new Cookie("foo", "bar");

        Cookies cookies = new Cookies();
        cookies.addCookie(cookie);

        Redirect redirect = new Redirect("/foo");
        redirect.setCookies(cookies);

        RedirectResponse response = new RedirectResponse(redirect);
        response.render(servletRequest, servletResponse);

        verify(servletResponse).addCookie(cookie);
    }
}
