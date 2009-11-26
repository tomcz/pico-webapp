package example.framework;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundResponse implements Response {

    public void render(ResponseContext response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
