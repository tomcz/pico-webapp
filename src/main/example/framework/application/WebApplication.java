package example.framework.application;

import example.framework.Application;
import example.framework.Component;
import example.framework.Container;
import example.framework.ErrorHandler;
import example.framework.IdentityFactory;
import example.framework.PathVariables;
import example.framework.RequestContext;
import example.framework.RequestMethod;
import example.framework.Response;
import example.utils.Pair;

import java.util.List;

public class WebApplication implements Application {

    private final Container applicationScope;
    private final List<Component> components;
    private final RouteFinder routeFinder;

    public WebApplication(Container container, RouteFinder routeFinder, List<Component> components) {
        this.applicationScope = container;
        this.routeFinder = routeFinder;
        this.components = components;

        for (Component component : components) {
            component.registerApplicationScope(applicationScope);
            component.registerRoutes(routeFinder);
        }
    }

    public Response process(RequestContext request) {
        RequestMethod method = request.getMethod();
        String lookupPath = request.getLookupPath();
        Container requestScope = createRequestScope(method);
        try {
            Pair<Route, PathVariables> mapping = routeFinder.findRoute(method, lookupPath, requestScope);
            PathVariables pathVars = mapping.getValue();
            Route route = mapping.getKey();

            IdentityFactory identityFactory = requestScope.get(IdentityFactory.class);
            return route.process(new WebRequest(request, identityFactory, pathVars));

        } catch (Exception e) {
            ErrorHandler handler = requestScope.get(ErrorHandler.class);
            return handler.handleError(method, lookupPath, e);

        } finally {
            requestScope.dispose();
        }
    }

    private Container createRequestScope(Object... instances) {
        Container requestScope = applicationScope.newChild();
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
