package example.framework;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiesTests {

    @Test
    public void shouldAddCookieToResponse() {
        Cookies cookies = new Cookies();
        cookies.addCookie("test", "cookie");

        HttpServletResponse response = mock(HttpServletResponse.class);
        cookies.addTo(response);

        ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(captor.capture());

        Cookie cookie = captor.getValue();
        assertThat(cookie.getName(), is("test"));
        assertThat(cookie.getValue(), is("cookie"));
    }
}
