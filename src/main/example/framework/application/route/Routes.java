package example.framework.application.route;

import example.framework.AccessFilter;
import example.framework.Container;
import example.framework.PathVariables;
import example.framework.RequestMethod;
import example.framework.Response;
import example.framework.RouteRegistry;
import example.framework.URIPatternFactory;
import example.framework.application.Route;
import example.framework.application.RouteFinder;
import example.utils.Pair;
import org.apache.log4j.Logger;
import org.weborganic.furi.URIPattern;
import org.weborganic.furi.URIResolveResult;
import org.weborganic.furi.URIResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static example.utils.GenericCollections.newArrayList;
import static example.utils.GenericCollections.newHashMap;
import static example.utils.GenericCollections.newHashSet;

public class Routes implements RouteRegistry, RouteFinder {

    private final Logger logger = Logger.getLogger(getClass());

    private final Map<RequestMethod, RouteFactory> routeFactories = newHashMap();

    private final List<URIPattern> templates = newArrayList();
    private final Map<URIPattern, Set<Class>> handlers = newHashMap();
    private final Map<Class, List<Class<? extends AccessFilter>>> filters = newHashMap();

    public Routes() {
        routeFactories.put(RequestMethod.GET, new PresenterRouteFactory());
        routeFactories.put(RequestMethod.POST, new CommandRouteFactory());
    }

    public void registerRoute(Class handlerType, Class<? extends AccessFilter>... accessFilters) {
        URIPattern template = URIPatternFactory.create(handlerType);
        if (handlers.containsKey(template)) {
            handlers.get(template).add(handlerType);
        } else {
            handlers.put(template, newHashSet(handlerType));
            templates.add(template);
        }
        if (accessFilters.length > 0) {
            filters.put(handlerType, Arrays.asList(accessFilters));
        }
    }

    public Pair<Route, PathVariables> findRoute(RequestMethod method, String lookupPath, Container container) {
        RouteFactory routeFactory = routeFactories.get(method);
        if (routeFactory == null) {
            logger.info("Cannot create routes for HTTP " + method + " method");
            return routeFor(new MethodNotAllowedResponse(routeFactories.keySet()));
        }
        URIResolveResult result = findTemplate(lookupPath);
        if (result == null) {
            logger.info("Cannot find template for " + lookupPath);
            return routeFor(new NotFoundResponse());
        }
        Route route = createRoute(routeFactory, result.getURIPattern(), container);
        if (route == null) {
            logger.info(String.format("%s %s not allowed for %s", method, lookupPath, result));
            return routeFor(new MethodNotAllowedResponse(allowedMethods(result.getURIPattern())));
        }
        return Pair.create(route, resolvePathVariables(result));
    }

    private Pair<Route, PathVariables> routeFor(Response response) {
        Route route = new ResponseWrappingRoute(response);
        return Pair.create(route, new PathVariables());
    }

    private URIResolveResult findTemplate(String lookupPath) {
        URIResolver resolver = new URIResolver(lookupPath);
        URIPattern template = resolver.find(templates);
        if (template == null) {
            return null;
        }
        return resolver.resolve(template);
    }

    private Route createRoute(RouteFactory factory, URIPattern template, Container container) {
        for (Class handlerType : handlers.get(template)) {
            if (factory.canCreateRouteFor(handlerType)) {
                Route route = factory.createRoute(container, handlerType);
                return applyAccessFilters(route, handlerType, container);
            }
        }
        return null;
    }

    private Route applyAccessFilters(Route route, Class handlerType, Container container) {
        if (filters.containsKey(handlerType)) {
            List<AccessFilter> accessFilters = newArrayList();
            for (Class<? extends AccessFilter> filter : filters.get(handlerType)) {
                accessFilters.add(container.get(filter));
            }
            return new AccessFilterRoute(route, accessFilters);
        }
        return route;
    }

    private Set<RequestMethod> allowedMethods(URIPattern template) {
        Set<RequestMethod> allowed = newHashSet();
        Set<Class> handlerTypes = handlers.get(template);
        for (Entry<RequestMethod, RouteFactory> entry : routeFactories.entrySet()) {
            for (Class handler : handlerTypes) {
                if (entry.getValue().canCreateRouteFor(handler)) {
                    allowed.add(entry.getKey());
                }
            }
        }
        return allowed;
    }

    private PathVariables resolvePathVariables(URIResolveResult result) {
        PathVariables pathVars = new PathVariables();
        for (String name : result.names()) {
            pathVars.set(name, result.get(name).toString());
        }
        return pathVars;
    }
}
