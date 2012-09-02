package example.framework;

public interface RouteRegistry {
    void registerRoute(Class handlerType, Class<? extends AccessFilter>... filters);
}
