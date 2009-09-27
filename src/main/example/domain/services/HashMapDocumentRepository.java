package example.domain.services;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.framework.Identity;
import example.utils.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashMapDocumentRepository implements DocumentRepository {

    private final Map<Identity, Document> repository = Maps.create();

    public List<Identity> getCurrentDocumentIDs() {
        return new ArrayList<Identity>(repository.keySet());
    }

    public Document get(Identity documentId) {
        Document document = repository.get(documentId);
        if (document == null) {
            document = new Document(documentId);
        }
        return document;
    }

    public void set(Document document) {
        Identity identity = document.getIdentity();
        if (identity.isNew()) {
            throw new IllegalArgumentException("Cannot save document with '" + identity + "' identity");
        }
        repository.put(identity, document);
    }
}
