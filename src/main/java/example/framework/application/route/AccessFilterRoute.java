package example.framework.application.route;

import example.framework.AccessFilter;
import example.framework.Request;
import example.framework.Response;
import example.framework.application.Route;

import java.util.List;

public class AccessFilterRoute implements Route {

    private final Route delegate;
    private final List<AccessFilter> filters;

    public AccessFilterRoute(Route delegate, List<AccessFilter> filters) {
        this.delegate = delegate;
        this.filters = filters;
    }

    public Response process(Request request) {
        for (AccessFilter filter : filters) {
            Response response = filter.check(request);
            if (response != null) {
                return response;
            }
        }
        return delegate.process(request);
    }
}
