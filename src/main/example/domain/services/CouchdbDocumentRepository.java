package example.domain.services;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.Identity;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Properties;

public class CouchdbDocumentRepository implements DocumentRepository {

    private final CouchdbHttp client;
    private final CouchdbJson json;

    public CouchdbDocumentRepository(Properties configuration) {
        client = new CouchdbHttp(configuration.getProperty("couchdb.url"));
        json = new CouchdbJson();
    }

    public List<Identity> getCurrentDocumentIDs() {
        String response = client.get("_design/documents/_view/get_all_ids");
        Validate.notNull(response, "Cannot get list of current document IDs");
        return json.parseIdentities(response);
    }

    public Document get(Identity identity) {
        if (identity.isNew()) {
            return new Document(identity);
        }
        String response = client.get(identity.getValue());
        if (response == null) {
            return new Document(identity);
        }
        return json.unmarshall(identity, response);
    }

    public void set(Document document) {
        Identity identity = document.getIdentity();
        if (identity.isNew()) {
            throw new IllegalArgumentException("Cannot save document with '" + identity + "' identity");
        }
        String request = json.marshall(document);
        String response = client.put(identity.getValue(), request);
        json.updateVersion(document, response);
    }
}
