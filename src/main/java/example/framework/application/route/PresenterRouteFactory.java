package example.framework.application.route;

import example.framework.Container;
import example.framework.Presenter;
import example.framework.application.Route;

public class PresenterRouteFactory implements RouteFactory {

    public boolean canCreateRouteFor(Class handlerType) {
        return Presenter.class.isAssignableFrom(handlerType);
    }

    @SuppressWarnings({"unchecked"})
    public Route createRoute(Container container, Class handlerType) {
        return new PresenterRoute((Presenter) container.get(handlerType));
    }
}
