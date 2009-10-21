package example.framework;

import example.utils.Lists;
import example.utils.Maps;
import example.utils.Pair;
import example.utils.Sets;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteFinder implements RouteRegistry {

    private final Logger logger = Logger.getLogger(getClass());

    private final Map<URITemplate, Set<Class>> handlers = Maps.create();
    private final Map<Class, List<Class<? extends AccessFilter>>> filters = Maps.create();
    private final Map<RequestMethod, List<RouteFactory>> routeFactories = Maps.create();

    public RouteFinder() {
        addRouteFactory(RequestMethod.GET, new PresenterRouteFactory());

        addRouteFactory(RequestMethod.POST, new CommandRouteFactory());
        addRouteFactory(RequestMethod.POST, new HandlerRouteFactory());

        addRouteFactory(RequestMethod.PUT, new HandlerRouteFactory());
        addRouteFactory(RequestMethod.HEAD, new HandlerRouteFactory());
        addRouteFactory(RequestMethod.DELETE, new HandlerRouteFactory());
    }

    private void addRouteFactory(RequestMethod method, RouteFactory factory) {
        List<RouteFactory> factories = routeFactories.get(method);
        if (factories == null) {
            factories = Lists.create();
            routeFactories.put(method, factories);
        }
        factories.add(factory);
    }

    public void registerRoute(Class handlerType, Class<? extends AccessFilter>... accessFilters) {
        URITemplate template = URITemplateFactory.createFrom(handlerType);
        if (handlers.containsKey(template)) {
            handlers.get(template).add(handlerType);
        } else {
            handlers.put(template, Sets.create(handlerType));
        }
        if (accessFilters.length > 0) {
            filters.put(handlerType, Arrays.asList(accessFilters));
        }
    }

    public Pair<Route, Map<String, String>> findRoute(RequestMethod method, String lookupPath, Container container) {
        List<RouteFactory> factories = routeFactories.get(method);
        if (factories == null) {
            logger.info("Cannot create routes for HTTP " + method + " method");
            return routeFor(new MethodNotAllowedResponse(routeFactories.keySet()));
        }
        URITemplate template = findTemplate(lookupPath);
        if (template == null) {
            logger.info("Cannot find template for " + lookupPath);
            return routeFor(new NotFoundResponse());
        }
        Route route = createRoute(factories, template, container);
        if (route == null) {
            logger.info(String.format("%s %s not allowed for %s", method, lookupPath, template));
            return routeFor(new MethodNotAllowedResponse(allowedMethods(template)));
        }
        return Pair.create(route, template.parse(lookupPath));
    }

    private Pair<Route, Map<String, String>> routeFor(Response response) {
        Route route = new ResponseWrappingRoute(response);
        Map<String, String> pathVars = Collections.emptyMap();
        return Pair.create(route, pathVars);
    }

    private URITemplate findTemplate(String lookupPath) {
        for (URITemplate template : handlers.keySet()) {
            if (template.matches(lookupPath)) {
                return template;
            }
        }
        return null;
    }

    private Route createRoute(List<RouteFactory> factories, URITemplate template, Container container) {
        Set<Class> handlerTypes = handlers.get(template);
        for (RouteFactory factory : factories) {
            for (Class handlerType : handlerTypes) {
                if (factory.canCreateRouteFor(handlerType)) {
                    Route route = factory.createRoute(container, handlerType);
                    return applyAccessFilters(route, handlerType, container);
                }
            }
        }
        return null;
    }

    private Route applyAccessFilters(Route route, Class handlerType, Container container) {
        if (filters.containsKey(handlerType)) {
            List<AccessFilter> accessFilters = Lists.create();
            for (Class<? extends AccessFilter> filter : filters.get(handlerType)) {
                accessFilters.add(container.get(filter));
            }
            return new AccessFilterRoute(route, accessFilters);
        }
        return route;
    }

    private Set<RequestMethod> allowedMethods(URITemplate template) {
        Set<RequestMethod> allowed = Sets.createLinked();
        Set<Class> handlerTypes = handlers.get(template);
        for (RequestMethod method : routeFactories.keySet()) {
            for (RouteFactory factory : routeFactories.get(method)) {
                for (Class handlerType : handlerTypes) {
                    if (factory.canCreateRouteFor(handlerType)) {
                        allowed.add(method);
                    }
                }
            }
        }
        return allowed;
    }
}
