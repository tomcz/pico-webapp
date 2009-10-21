package example.framework;

import static example.framework.ResponseUtils.addHeaderToResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderResponse implements Response {

    private final Header header;

    public HeaderResponse(Header header) {
        this.header = header;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (header != null) {
            addHeaderToResponse(response, header);
            response.sendError(200);
        }
    }
}
