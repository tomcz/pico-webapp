package example.domain.services;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.Identity;

import java.util.List;

public class CouchdbDocumentRepository implements DocumentRepository {

    private final CouchdbHttp client = new CouchdbHttp();
    private final CouchdbJson json = new CouchdbJson();

    public List<Identity> getCurrentDocumentIDs() {
        String response = client.get("http://localhost:5984/pico/_design/documents/_view/get_all_ids");
        return json.parseIdentities(response);
    }

    public Document get(Identity documentId) {
        if (documentId.isNew()) {
            return new Document(documentId);
        }
        String response = client.get("http://localhost:5984/pico/" + documentId);
        if (response == null) {
            return new Document(documentId);
        }
        return json.unmarshall(documentId, response);
    }

    public void set(Document document) {
        Identity identity = document.getIdentity();
        if (identity.isNew()) {
            throw new IllegalArgumentException("Cannot save document with '" + identity + "' identity");
        }
        String request = json.marshall(document);
        String response = client.put("http://localhost:5984/pico/" + document.getIdentity(), request);
        json.updateVersion(document, response);
    }
}
