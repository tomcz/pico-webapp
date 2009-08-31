package example.domain.services;

import example.framework.ComponentAdaptor;
import example.framework.Container;

public class ServicesComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container applicationScope) {
        applicationScope.register(HashMapDocumentRepository.class);
        //applicationScope.register(CouchdbDocumentRepository.class);
    }

    public void registerRequestScope(Container requestScope) {
        requestScope.register(SimpleDocumentValidator.class);
    }
}
