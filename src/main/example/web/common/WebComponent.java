package example.web.common;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RequestMethod;
import example.framework.RouteRegistry;

public class WebComponent extends ComponentAdaptor {

    public void registerRequestScope(Container requestScope) {
        requestScope.register(RedirectOnErrorHandler.class);
        requestScope.register(ErrorReferencePresenter.class);
        registerIdentityFactory(requestScope);
    }

    public void registerRoutes(RouteRegistry routeRegistry) {
        routeRegistry.registerRoute(ErrorReferencePresenter.class);
    }

    private void registerIdentityFactory(Container requestScope) {
        RequestMethod method = requestScope.get(RequestMethod.class);
        switch (method) {
            case GET:
                requestScope.register(IgnoreNewIdentityFactory.class);
                break;
            case POST:
                requestScope.register(CreateOnNewIdentityFactory.class);
                break;
        }
    }
}
