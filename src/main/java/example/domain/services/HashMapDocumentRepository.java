package example.domain.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import example.domain.Document;
import example.domain.DocumentRepository;
import example.framework.Identity;

import java.util.List;
import java.util.Map;

public class HashMapDocumentRepository implements DocumentRepository {

    private final Map<Identity, Document> repository = Maps.newConcurrentMap();

    public Document get(Identity documentId) {
        Document document = repository.get(documentId);
        if (document == null) {
            document = new Document(documentId);
        }
        return document;
    }

    public void set(Document document) {
        Identity identity = document.getId();
        if (identity.isNew()) {
            throw new IllegalArgumentException("Cannot save document with '" + identity + "' identity");
        }
        repository.put(identity, document);
    }

    public List<Document> getAll() {
        return Lists.newArrayList(repository.values());
    }
}
