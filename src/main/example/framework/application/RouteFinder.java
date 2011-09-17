package example.framework.application;

import example.framework.Container;
import example.framework.PathVariables;
import example.framework.RequestMethod;
import example.framework.RouteRegistry;
import org.apache.commons.lang3.tuple.Pair;

public interface RouteFinder extends RouteRegistry {
    Pair<Route, PathVariables> findRoute(RequestMethod method, String lookupPath, Container container);
}
