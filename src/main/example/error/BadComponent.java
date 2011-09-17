package example.error;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RouteRegistry;

@SuppressWarnings({"unchecked"})
public class BadComponent extends ComponentAdaptor {

    @Override
    public void registerRequestScope(Container requestScope) {
        requestScope.register(BadPresener.class);
    }

    @Override
    public void registerRoutes(RouteRegistry routeRegistry) {
        routeRegistry.registerRoute(BadPresener.class);
    }
}
