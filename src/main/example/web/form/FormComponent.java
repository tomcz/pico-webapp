package example.web.form;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RouteRegistry;

public class FormComponent extends ComponentAdaptor {

    public void registerRequestScope(Container requestScope) {
        requestScope.register(SuccessPresenter.class);
        requestScope.register(IndexPresenter.class);
        requestScope.register(FatalPresener.class);
        requestScope.register(FormPresenter.class);
        requestScope.register(FormCommand.class);
    }

    public void registerRoutes(RouteRegistry routeRegistry) {
        routeRegistry.registerRoute(SuccessPresenter.class);
        routeRegistry.registerRoute(IndexPresenter.class);
        routeRegistry.registerRoute(FatalPresener.class);
        routeRegistry.registerRoute(FormPresenter.class);
        routeRegistry.registerRoute(FormCommand.class);
    }
}
