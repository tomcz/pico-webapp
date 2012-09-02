package example.framework;

public interface Component {

    void registerApplicationScope(Container applicationScope);

    void registerRequestScope(Container requestScope);

    void registerRoutes(RouteRegistry routeRegistry);
}
