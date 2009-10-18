package example.framework;

public interface RouteFactory {

    boolean canCreateRouteFor(Class handlerType);

    Route createRoute(Container container, Class handlerType);
}
