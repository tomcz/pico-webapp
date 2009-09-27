package example.domain.web;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.framework.Command;
import example.framework.Identity;
import example.framework.Redirect;
import example.framework.Request;
import example.framework.RouteMapping;
import static example.domain.web.DocumentUtils.setProperties;

@RouteMapping("/form/{documentId}")
public class FormCommand implements Command {

    private final DocumentRepository repository;
    private final DocumentValidator validator;

    public FormCommand(DocumentRepository repository, DocumentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Redirect execute(Request request) {
        Identity documentId = request.getIdentity("documentId");

        Document document = repository.get(documentId);

        setProperties(request, document);
        validator.validate(document);
        repository.set(document);

        if (document.isValid()) {
            return new Redirect(SuccessPresenter.class, "documentId", documentId);
        }
        return new Redirect(FormPresenter.class, "documentId", documentId);
    }
}
