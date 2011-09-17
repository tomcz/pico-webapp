package example.framework.template;

import example.framework.ComponentAdaptor;
import example.framework.Container;

public class TemplateComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container scope) {
        scope.register(FreemarkerTemplateFactory.class);
//        scope.register(WebTemplateFactory.class, scope.newArgs().autowired().constant("/templates"));
    }
}
