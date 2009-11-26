package example.framework.template;

import example.framework.ComponentAdaptor;
import static example.framework.ConstructorArgument.autowired;
import static example.framework.ConstructorArgument.constant;
import example.framework.Container;

public class TemplateComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container applicationScope) {
        applicationScope.register(WebTemplateFactory.class, autowired(), constant("/templates"));
    }
}
