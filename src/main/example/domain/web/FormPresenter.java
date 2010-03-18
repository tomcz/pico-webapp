package example.domain.web;

import example.domain.DocumentRepository;
import example.framework.Identity;
import example.framework.Location;
import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;
import example.framework.template.Template;
import example.framework.template.TemplateFactory;
import example.framework.template.TemplateResponse;

import static example.domain.web.DocumentUtils.createDocumentModel;

@RouteMapping("/form/{documentId}")
public class FormPresenter implements Presenter {

    private final DocumentRepository repository;
    private final TemplateFactory templateFactory;

    public FormPresenter(DocumentRepository repository, TemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
        this.repository = repository;
    }

    public Response display(Request request) {
        Identity documentId = request.getIdentity("documentId");

        Template template = templateFactory.create("example", "form");
        template.set("formAction", new Location(FormCommand.class, "documentId", documentId));
        template.set("document", createDocumentModel(repository.get(documentId)));
        template.set("indexLink", new Location(IndexPresenter.class));

        return new TemplateResponse(template);
    }
}
