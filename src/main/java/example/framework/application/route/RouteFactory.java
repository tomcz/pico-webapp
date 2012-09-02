package example.framework.application.route;

import example.framework.Container;
import example.framework.application.Route;

public interface RouteFactory {

    boolean canCreateRouteFor(Class handlerType);

    Route createRoute(Container container, Class handlerType);
}
