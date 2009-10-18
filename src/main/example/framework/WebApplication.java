package example.framework;

import example.utils.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class WebApplication implements Application {

    private final PicoContainer applicationScope = new PicoContainer();
    private final RouteFinder routeFinder = new RouteFinder();
    private final PathHelper pathHelper = new PathHelper();

    private List<Component> components;

    public WebApplication(List<Component> components, Object... instances) {
        this.components = components;
        for (Object instance : instances) {
            applicationScope.registerInstance(instance);
        }
        for (Component component : components) {
            component.registerApplicationScope(applicationScope);
            component.registerRoutes(routeFinder);
        }
    }

    public Response process(HttpServletRequest servletRequest, RequestMethod method) {
        String lookupPath = pathHelper.getLookupPathForRequest(servletRequest);
        PicoContainer requestScope = createRequestScope(method);
        try {
            Pair<Route, Map<String, String>> pair = routeFinder.findRoute(method, lookupPath, requestScope);
            Map<String, String> pathVars = pair.getValue();
            Route route = pair.getKey();

            IdentityFactory identityFactory = requestScope.get(IdentityFactory.class);
            return route.process(new WebRequest(servletRequest, identityFactory, pathVars));

        } catch (Exception e) {
            ErrorHandler handler = requestScope.get(ErrorHandler.class);
            return handler.handleError(method, lookupPath, e);

        } finally {
            requestScope.dispose();
        }
    }

    private PicoContainer createRequestScope(Object... instances) {
        PicoContainer requestScope = new PicoContainer(applicationScope);
        for (Object instance : instances) {
            requestScope.registerInstance(instance);
        }
        for (Component component : components) {
            component.registerRequestScope(requestScope);
        }
        return requestScope;
    }

    public void dispose() {
        applicationScope.dispose();
    }
}
