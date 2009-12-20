package example.framework;

import java.io.IOException;

public class HeaderResponse implements Response {

    private final Header header;

    public HeaderResponse(Header header) {
        this.header = header;
    }

    public void render(ResponseContext response) throws IOException {
        if (header != null) {
            response.setHeader(header);
        }
    }
}
