package example.web.form;

import example.domain.Document;
import example.domain.DocumentRepository;
import example.domain.DocumentValidator;
import example.domain.Identity;
import example.framework.Command;
import example.framework.Redirect;
import example.framework.Request;
import example.framework.RouteMapping;
import static example.web.DocumentUtils.setProperties;
import example.web.IdentityFactory;

@RouteMapping("/form/{documentId}")
public class FormCommand implements Command {

    private final IdentityFactory identityFactory;
    private final DocumentRepository repository;
    private final DocumentValidator validator;

    public FormCommand(IdentityFactory identityFactory, DocumentRepository repository, DocumentValidator validator) {
        this.identityFactory = identityFactory;
        this.repository = repository;
        this.validator = validator;
    }

    public Redirect execute(Request request) {
        Identity documentId = identityFactory.createFrom(request.getPathVariable("documentId"));

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
