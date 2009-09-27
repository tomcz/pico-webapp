package example.domain;

import example.framework.Identity;

import java.util.List;

public interface DocumentRepository {

    List<Identity> getCurrentDocumentIDs();

    Document get(Identity documentId);

    void set(Document document);
}
