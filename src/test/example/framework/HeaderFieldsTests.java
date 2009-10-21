package example.framework;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

public class HeaderFieldsTests {

    @Test
    public void shouldSetSingleHeaderValues() {
        HeaderFields headers = new HeaderFields();
        headers.add("one", "value1");
        headers.add("two", "value2");

        HttpServletResponse response = mock(HttpServletResponse.class);
        headers.addTo(response);

        verify(response).setHeader("one", "value1");
        verify(response).setHeader("two", "value2");
    }

    @Test
    public void shouldAddHeaderValueForSameKey() {
        HeaderFields headers = new HeaderFields();
        headers.add("one", "value1");
        headers.add("one", "value2");

        HttpServletResponse response = mock(HttpServletResponse.class);
        headers.addTo(response);

        verify(response).setHeader("one", "value1");
        verify(response).addHeader("one", "value2");
    }
}
