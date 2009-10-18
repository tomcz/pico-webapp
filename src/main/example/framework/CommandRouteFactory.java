package example.framework;

public class CommandRouteFactory implements RouteFactory {

    public boolean canCreateRouteFor(Class handlerType) {
        return Command.class.isAssignableFrom(handlerType);
    }

    @SuppressWarnings({"unchecked"})
    public Route createRoute(Container container, Class handlerType) {
        return new CommandRoute((Command) container.get(handlerType));
    }
}
