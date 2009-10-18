package example.framework;

public class ResponseWrappingRoute implements Route {

    private final Response response;

    public ResponseWrappingRoute(Response response) {
        this.response = response;
    }

    public Response process(Request request) {
        return response;
    }
}
