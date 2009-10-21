package example.framework;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusCodeResponseTests {

    @Test
    public void shouldSendErrorWithExpectedCode() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        StatusCode status = new StatusCode(200);

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(servletRequest, servletResponse);

        verify(servletResponse).sendError(200);
    }

    @Test
    public void shouldSendErrorWithExpectedCodeAndMessage() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        StatusCode status = new StatusCode(404, "oops");

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(servletRequest, servletResponse);

        verify(servletResponse).sendError(404, "oops");
    }

    @Test
    public void shouldAddHeadersToResponse() throws IOException {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);

        Headers headers = new Headers();
        headers.setHeader("foo", "bar");

        StatusCode status = new StatusCode(200);
        status.setHeaders(headers);

        StatusCodeResponse response = new StatusCodeResponse(status);
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

        StatusCode status = new StatusCode(200);
        status.setCookies(cookies);

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(servletRequest, servletResponse);

        verify(servletResponse).addCookie(cookie);
    }
}
