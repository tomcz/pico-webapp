package example.web.form;

import example.domain.DocumentRepository;
import example.domain.Identity;
import example.framework.Location;
import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;
import example.framework.template.Template;
import example.framework.template.TemplateFactory;
import example.framework.template.TemplateResponse;
import static example.web.DocumentUtils.createDocumentModel;
import example.web.IdentityFactory;

@RouteMapping("/success/{documentId}")
public class SuccessPresenter implements Presenter {

    private final DocumentRepository repository;
    private final TemplateFactory templateFactory;
    private final IdentityFactory identityFactory;

    public SuccessPresenter(DocumentRepository repository,
                            TemplateFactory templateFactory,
                            IdentityFactory identityFactory) {

        this.templateFactory = templateFactory;
        this.identityFactory = identityFactory;
        this.repository = repository;
    }

    public Response display(Request request) {
        Identity documentId = identityFactory.createFrom(request.getPathVariable("documentId"));

        Template template = templateFactory.create("example", "success");
        template.set("document", createDocumentModel(repository.get(documentId)));
        template.set("oldFormLink", new Location(FormPresenter.class, "documentId", documentId));
        template.set("newFormLink", new Location(FormPresenter.class, "documentId", Identity.NEW));
        template.set("indexLink", new Location(IndexPresenter.class));

        return new TemplateResponse(template);
    }
}
