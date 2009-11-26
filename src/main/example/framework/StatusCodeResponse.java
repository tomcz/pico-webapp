package example.framework;

import java.io.IOException;

public class StatusCodeResponse implements Response {

    private final StatusCode statusCode;

    public StatusCodeResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void render(ResponseContext response) throws IOException {
        if (statusCode != null) {
            response.setHeader(statusCode);
            sendError(response);
        }
    }

    private void sendError(ResponseContext response) throws IOException {
        String message = statusCode.getMessage();
        if (message != null) {
            response.sendError(statusCode.getCode(), message);
        } else {
            response.sendError(statusCode.getCode());
        }
    }
}
