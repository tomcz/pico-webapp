package example.framework;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

public class HeadersTests {

    @Test
    public void shouldSetSingleHeaderValues() {
        Headers headers = new Headers();
        headers.addHeader("one", "value1");
        headers.addHeader("two", "value2");

        HttpServletResponse response = mock(HttpServletResponse.class);
        headers.addTo(response);

        verify(response).setHeader("one", "value1");
        verify(response).setHeader("two", "value2");
    }

    @Test
    public void shouldAddHeaderValueForSameKey() {
        Headers headers = new Headers();
        headers.addHeader("one", "value1");
        headers.addHeader("one", "value2");

        HttpServletResponse response = mock(HttpServletResponse.class);
        headers.addTo(response);

        verify(response).setHeader("one", "value1");
        verify(response).addHeader("one", "value2");
    }
}
