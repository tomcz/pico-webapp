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
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static ch.lambdaj.Lambda.forEach;

public class WebApplication implements Application {

    private final Container applicationScope;
    private final List<Component> components;

    public WebApplication(List<Component> components, Object... instances) {
        this.components = components;

        this.applicationScope = new PicoContainer();
        this.applicationScope.registerInstances(instances);

        each(components).registerApplicationScope(applicationScope);
        each(components).registerRoutes(applicationScope.get(RouteRegistry.class));
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
        each(components).registerRequestScope(requestScope);

        return requestScope;
    }

    public void dispose() {
        applicationScope.dispose();
    }

    private static Component each(List<Component> components) {
        return forEach(components, Component.class);
    }
}
