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
import example.utils.Function;
import example.utils.Lists;
import example.utils.Pair;

import java.util.List;

@RouteMapping("/index")
public class IndexPresenter implements Presenter {

    private final DocumentRepository repository;
    private final TemplateFactory templateFactory;

    public IndexPresenter(DocumentRepository repository, TemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
        this.repository = repository;
    }

    public Response display(Request request) {
        List<Identity> identities = repository.getCurrentDocumentIDs();

        Template template = templateFactory.create("example", "index");
        template.set("mappings", Lists.map(identities, new IdentityMapper()));
        template.set("newForm", new Location(FormPresenter.class, "documentId", Identity.NEW));

        return template;
    }

    private static class IdentityMapper implements Function<Identity, Pair<Identity, Location>> {
        public Pair<Identity, Location> execute(Identity item) {
            return Pair.create(item, new Location(FormPresenter.class, "documentId", item));
        }
    }
}
