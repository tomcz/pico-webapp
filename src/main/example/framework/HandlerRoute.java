package example.framework;

public class HandlerRoute implements Route {

    private final Handler handler;

    public HandlerRoute(Handler handler) {
        this.handler = handler;
    }

    public Response process(Request request) {
        StatusCode statusCode = handler.handle(request);
        return new StatusCodeResponse(statusCode);
    }
}
