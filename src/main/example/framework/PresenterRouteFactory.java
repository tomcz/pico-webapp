package example.framework;

public class PresenterRouteFactory implements RouteFactory {

    public boolean canCreateRouteFor(Class handlerType) {
        return Presenter.class.isAssignableFrom(handlerType);
    }

    @SuppressWarnings({"unchecked"})
    public Route createRoute(Container container, Class handlerType, URITemplate template) {
        return new PresenterRoute((Presenter) container.get(handlerType), template);
    }
}
