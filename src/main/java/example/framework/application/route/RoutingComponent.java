package example.framework.application.route;

import example.framework.ComponentAdaptor;
import example.framework.Container;

public class RoutingComponent extends ComponentAdaptor {

    @Override
    public void registerApplicationScope(Container applicationScope) {
        applicationScope.register(Routes.class);
    }
}
