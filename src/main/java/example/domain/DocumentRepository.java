package example.domain;

import example.framework.Identity;

import java.util.List;

public interface DocumentRepository {

    Document get(Identity documentId);

    void set(Document document);

    List<Document> getAll();
}
