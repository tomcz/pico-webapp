package example.framework.application.route;

import example.framework.Response;
import example.framework.ResponseContext;
import example.framework.StatusCode;

import java.io.IOException;

public class StatusCodeResponse implements Response {

    private final StatusCode statusCode;

    public StatusCodeResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void render(ResponseContext response) throws IOException {
        if (statusCode != null) {
            response.setStatusCode(statusCode.getCode());
            response.setHeader(statusCode);
        }
    }
}
