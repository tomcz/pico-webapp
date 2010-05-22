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
import example.framework.RouteRegistry;
import example.utils.Pair;

import java.util.List;

import static ch.lambdaj.Lambda.forEach;

public class WebApplication implements Application {

    private final Container applicationScope;
    private final List<Component> components;

    public WebApplication(Container applicationScope, List<Component> components) {
        registerApplicationScope(applicationScope, components);
        registerRoutes(applicationScope, components);
        this.applicationScope = applicationScope;
        this.components = components;
    }

    private static void registerApplicationScope(Container container, List<Component> components) {
        forEach(components, Component.class).registerApplicationScope(container);
    }

    private static void registerRoutes(Container container, List<Component> components) {
        RouteRegistry registry = container.get(RouteRegistry.class);
        forEach(components, Component.class).registerRoutes(registry);
    }

    public Response process(RequestContext request) {
        RequestMethod method = request.getMethod();
        String lookupPath = request.getLookupPath();

        Container requestScope = createRequestScope(method);
        try {
            RouteFinder finder = requestScope.get(RouteFinder.class);
            Pair<Route, PathVariables> mapping = finder.findRoute(method, lookupPath, requestScope);

            Route route = mapping.getKey();
            PathVariables pathVars = mapping.getValue();

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
        requestScope.registerInstances(instances);
        forEach(components, Component.class).registerRequestScope(requestScope);
        return requestScope;
    }

    public void dispose() {
        applicationScope.dispose();
    }
}
