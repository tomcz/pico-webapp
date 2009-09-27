package example.domain.web;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RouteRegistry;

public class WebComponent extends ComponentAdaptor {

    public void registerRequestScope(Container requestScope) {
        requestScope.register(SuccessPresenter.class);
        requestScope.register(IndexPresenter.class);
        requestScope.register(FormPresenter.class);
        requestScope.register(FormCommand.class);
    }

    public void registerRoutes(RouteRegistry routeRegistry) {
        routeRegistry.registerRoute(SuccessPresenter.class);
        routeRegistry.registerRoute(IndexPresenter.class);
        routeRegistry.registerRoute(FormPresenter.class);
        routeRegistry.registerRoute(FormCommand.class);
    }
}
