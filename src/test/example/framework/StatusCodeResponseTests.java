package example.framework;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

public class StatusCodeResponseTests {

    @Test
    public void shouldSendErrorWithExpectedCode() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        StatusCode status = new StatusCode(200);

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(context);

        verify(context).sendError(200);
    }

    @Test
    public void shouldSendErrorWithExpectedCodeAndMessage() throws IOException {
        ResponseContext context = mock(ResponseContext.class);

        StatusCode status = new StatusCode(404, "oops");

        StatusCodeResponse response = new StatusCodeResponse(status);
        response.render(context);

        verify(context).sendError(404, "oops");
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
