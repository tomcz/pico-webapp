package example.domain.services;

import example.framework.ComponentAdaptor;
import example.framework.Configuration;
import example.framework.Container;
import org.apache.commons.lang.BooleanUtils;

public class ServicesComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container scope) {
        Configuration configuration = scope.get(Configuration.class);
        if (BooleanUtils.toBoolean(configuration.get("use.couchdb"))) {
            scope.register(CouchdbDocumentRepository.class, scope.newArgs().configuredProperty("couchdb.url"));
        } else {
            scope.register(HashMapDocumentRepository.class);
        }
    }

    public void registerRequestScope(Container scope) {
        scope.register(SimpleDocumentValidator.class);
    }
}
