package example.framework.servlet;

import example.framework.Header;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServletResponseContextTests {

    @Test
    public void shouldAddCookieToResponse() {
        Cookie cookie = new Cookie("test", "cookie");

        Header header = new Header();
        header.addCookie(cookie);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ServletResponseContext context = new ServletResponseContext(request, response);
        context.setHeader(header);

        verify(response).addCookie(cookie);
    }

    @Test
    public void shouldSetSingleHeaderValues() {
        Header header = new Header();
        header.setField("one", "value1");
        header.setField("two", "value2");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ServletResponseContext context = new ServletResponseContext(request, response);
        context.setHeader(header);

        verify(response).setHeader("one", "value1");
        verify(response).setHeader("two", "value2");
    }

    @Test
    public void shouldAddHeaderValueForSameKey() {
        Header header = new Header();
        header.setField("one", "value1", "value2");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ServletResponseContext context = new ServletResponseContext(request, response);
        context.setHeader(header);

        verify(response).setHeader("one", "value1");
        verify(response).addHeader("one", "value2");
    }
}
