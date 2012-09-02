package example.framework.application.route;

import example.framework.Command;
import example.framework.Container;
import example.framework.application.Route;

public class CommandRouteFactory implements RouteFactory {

    public boolean canCreateRouteFor(Class handlerType) {
        return Command.class.isAssignableFrom(handlerType);
    }

    @SuppressWarnings({"unchecked"})
    public Route createRoute(Container container, Class handlerType) {
        return new CommandRoute((Command) container.get(handlerType));
    }
}
