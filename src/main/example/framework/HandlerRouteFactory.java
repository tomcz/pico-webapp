package example.framework;

public class HandlerRouteFactory implements RouteFactory {

    public boolean canCreateRouteFor(Class handlerType) {
        return Handler.class.isAssignableFrom(handlerType);
    }

    @SuppressWarnings({"unchecked"})
    public Route createRoute(Container container, Class handlerType) {
        return new HandlerRoute((Handler) container.get(handlerType));
    }
}
