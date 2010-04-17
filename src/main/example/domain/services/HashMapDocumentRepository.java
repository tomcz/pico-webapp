package example.domain.services;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.framework.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static example.utils.GenericCollections.newHashMap;

public class HashMapDocumentRepository implements DocumentRepository {

    private final Map<Identity, Document> repository = newHashMap();

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

    public List<Document> getAll() {
        return new ArrayList<Document>(repository.values());
    }
}
