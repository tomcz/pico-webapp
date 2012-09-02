package example.framework.application.route;

import example.framework.ResponseContext;
import example.framework.StatusCode;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StatusCodeResponseTests {

    @Test
    public void shouldSetStatusCodeWithExpectedCode() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        StatusCode status = new StatusCode(200);

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(context);

        verify(context).setStatusCode(200);
    }

    @Test
    public void shouldAddHeaderToResponse() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        StatusCode status = new StatusCode(200);

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(context);

        verify(context).setHeader(status);
    }
}
