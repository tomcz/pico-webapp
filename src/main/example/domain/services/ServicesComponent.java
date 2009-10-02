package example.domain.services;

import example.framework.ComponentAdaptor;
import static example.framework.ConstructorArgument.configuredProperty;
import example.framework.Container;
import org.apache.commons.lang.BooleanUtils;

import java.util.Properties;

public class ServicesComponent extends ComponentAdaptor {

    public void registerApplicationScope(Container applicationScope) {
        Properties configuration = applicationScope.get(Properties.class);
        if (BooleanUtils.toBoolean(configuration.getProperty("use.couchdb"))) {
            applicationScope.register(CouchdbDocumentRepository.class, configuredProperty("couchdb.url"));
        } else {
            applicationScope.register(HashMapDocumentRepository.class);
        }
    }

    public void registerRequestScope(Container requestScope) {
        requestScope.register(SimpleDocumentValidator.class);
    }
}
