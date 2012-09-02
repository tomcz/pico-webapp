package example.domain.services;

import example.framework.ComponentAdaptor;
import example.framework.Container;

public class ServicesComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container scope) {
        scope.register(HashMapDocumentRepository.class);
    }

    public void registerRequestScope(Container scope) {
        scope.register(SimpleDocumentValidator.class);
    }
}
