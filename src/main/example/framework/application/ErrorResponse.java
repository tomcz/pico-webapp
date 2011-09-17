package example.framework.application;

import example.framework.Response;
import example.framework.ResponseContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponse implements Response {

    private int errorCode;
    private String errorMessage;

    private ErrorResponse(int errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static ErrorResponse notFound() {
        return new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, null);
    }

    public static ErrorResponse internalError(String errorRef) {
        return new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorRef);
    }

    public void render(ResponseContext response) throws IOException {
        if (errorMessage != null) {
            response.sendError(errorCode, errorMessage);
        } else {
            response.sendError(errorCode);
        }
    }
}
