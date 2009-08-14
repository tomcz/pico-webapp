package example.domain;

import java.util.List;

public interface DocumentRepository {

    List<Identity> getCurrentDocumentIDs();

    Document get(Identity documentId);

    void set(Document document);
}
