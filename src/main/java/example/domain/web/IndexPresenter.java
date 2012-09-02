package example.domain.web;

import com.google.common.base.Function;
import example.domain.Document;
import example.domain.DocumentRepository;
import example.framework.Identity;
import example.framework.Location;
import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;
import example.framework.template.Template;
import example.framework.template.TemplateFactory;
import example.utils.Eagerly;
import org.apache.commons.lang3.tuple.Pair;

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
        Template template = templateFactory.create("example", "index");
        template.set("newForm", new Location(FormPresenter.class, "documentId", Identity.NEW));
        template.set("mappings", mappings(repository.getAll()));
        return template;
    }

    private List<Pair<Document, Location>> mappings(List<Document> documents) {
        return Eagerly.transform(documents, new Function<Document, Pair<Document, Location>>() {
            public Pair<Document, Location> apply(Document doc) {
                return Pair.of(doc, new Location(FormPresenter.class, "documentId", doc.getId()));
            }
        });
    }
}
