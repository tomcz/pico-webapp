package example.framework;

public interface Route {

    Class getHandlerType();

    URITemplate getTemplate();

    Response process(Request request);
}
