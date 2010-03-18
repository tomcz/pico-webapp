package example.framework.application.route;

import example.framework.Request;
import example.framework.Response;
import example.framework.application.Route;

public class ResponseWrappingRoute implements Route {

    private final Response response;

    public ResponseWrappingRoute(Response response) {
        this.response = response;
    }

    public Response process(Request request) {
        return response;
    }
}
