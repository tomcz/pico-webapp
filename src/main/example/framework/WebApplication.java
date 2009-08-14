package example.framework;

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
        Container requestScope = createRequestScope(method);
        try {
            Route route = routeFinder.findRoute(method, lookupPath, requestScope);
            Map<String, String> pathVariables = route.getTemplate().parse(lookupPath);
            return route.process(new WebRequest(servletRequest, pathVariables));

        } catch (Exception e) {
            ErrorHandler handler = requestScope.get(ErrorHandler.class);
            return handler.handleError(method, lookupPath, e);

        } finally {
            requestScope.dispose();
        }
    }

    private Container createRequestScope(Object... instances) {
        Container requestScope = new PicoContainer(applicationScope);
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
