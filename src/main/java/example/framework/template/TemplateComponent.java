package example.framework.template;

import example.framework.ComponentAdaptor;
import example.framework.Container;

public class TemplateComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container scope) {
        scope.register(FreemarkerTemplateFactory.class);
    }
}
