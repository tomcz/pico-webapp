package example.framework.application.error;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RouteRegistry;

@SuppressWarnings({"unchecked"})
public class ErrorComponent extends ComponentAdaptor {

    @Override
    public void registerRequestScope(Container requestScope) {
        requestScope.register(ErrorReferencePresenter.class);
        requestScope.register(RedirectOnErrorHandler.class);
    }

    @Override
    public void registerRoutes(RouteRegistry routeRegistry) {
        routeRegistry.registerRoute(ErrorReferencePresenter.class);
    }
}
