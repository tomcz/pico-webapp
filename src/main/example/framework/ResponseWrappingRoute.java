package example.framework;

public class ResponseWrappingRoute implements Route {

    private final Response response;

    public ResponseWrappingRoute(Response response) {
        this.response = response;
    }

    public Class getHandlerType() {
        return response.getClass();
    }

    public URITemplate getTemplate() {
        return URITemplateFactory.createFrom("/");
    }

    public Response process(Request request) {
        return response;
    }
}
