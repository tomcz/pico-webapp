package example.framework;

import java.util.List;

public class AccessFilterRoute implements Route {

    private final Route delegate;
    private final List<AccessFilter> filters;

    public AccessFilterRoute(Route delegate, List<AccessFilter> filters) {
        this.delegate = delegate;
        this.filters = filters;
    }

    public Class getHandlerType() {
        return delegate.getHandlerType();
    }

    public URITemplate getTemplate() {
        return delegate.getTemplate();
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
