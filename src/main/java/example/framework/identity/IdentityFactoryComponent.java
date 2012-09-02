package example.framework.identity;

import example.framework.ComponentAdaptor;
import example.framework.Container;
import example.framework.RequestMethod;

public class IdentityFactoryComponent extends ComponentAdaptor {

    public void registerRequestScope(Container requestScope) {
        RequestMethod method = requestScope.get(RequestMethod.class);
        switch (method) {
            case GET:
                requestScope.register(IgnoreNewIdentityFactory.class);
                break;
            case POST:
                requestScope.register(CreateOnNewIdentityFactory.class);
                break;
        }
    }
}
