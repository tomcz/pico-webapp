package example.framework;

import example.utils.Lists;
import example.utils.Maps;
import example.utils.Sets;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteFinder implements RouteRegistry {

    private final Logger logger = Logger.getLogger(getClass());

    private final Map<URITemplate, Set<Class>> handlers = Maps.create();
    private final Map<Class, List<Class<? extends AccessFilter>>> filters = Maps.create();

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

    public Route findRoute(RequestMethod method, String lookupPath, Container container) {
        RouteFactory routeFactory = findRouteFactory(method);
        if (routeFactory == null) {
            logger.info("Cannot create routes for HTTP " + method + " methods");
            return new ResponseWrappingRoute(new MethodNotAllowedResponse());
        }
        URITemplate template = findTemplate(lookupPath);
        if (template == null) {
            logger.info("Cannot find template for " + lookupPath);
            return new ResponseWrappingRoute(new NotFoundResponse());
        }
        Route route = createRoute(routeFactory, template, container);
        if (route == null) {
            logger.info(String.format("%s %s not allowed for %s", method, lookupPath, template));
            route = new ResponseWrappingRoute(new MethodNotAllowedResponse());
        }
        return route;
    }

    private RouteFactory findRouteFactory(RequestMethod method) {
        switch (method) {
            case GET:
                return new PresenterRouteFactory();
            case POST:
                return new CommandRouteFactory();
            default:
                return null;
        }
    }

    private URITemplate findTemplate(String lookupPath) {
        for (URITemplate template : handlers.keySet()) {
            if (template.matches(lookupPath)) {
                return template;
            }
        }
        return null;
    }

    private Route createRoute(RouteFactory factory, URITemplate template, Container container) {
        for (Class handlerType : handlers.get(template)) {
            if (factory.canCreateRouteFor(handlerType)) {
                Route route = factory.createRoute(container, handlerType, template);
                return applyAccessFilters(route, handlerType, container);
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
}
