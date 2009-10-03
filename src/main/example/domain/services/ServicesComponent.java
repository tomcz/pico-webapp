package example.domain.services;

import example.framework.ComponentAdaptor;
import example.framework.Configuration;
import static example.framework.ConstructorArgument.configuredProperty;
import example.framework.Container;
import org.apache.commons.lang.BooleanUtils;

public class ServicesComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container applicationScope) {
        Configuration configuration = applicationScope.get(Configuration.class);
        if (BooleanUtils.toBoolean(configuration.get("use.couchdb"))) {
            applicationScope.register(CouchdbDocumentRepository.class, configuredProperty("couchdb.url"));
        } else {
            applicationScope.register(HashMapDocumentRepository.class);
        }
    }

    public void registerRequestScope(Container requestScope) {
        requestScope.register(SimpleDocumentValidator.class);
    }
}
