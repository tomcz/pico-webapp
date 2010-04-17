package example.domain.web;

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
import example.utils.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import java.util.ArrayList;
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
        List<Document> documents = repository.getAll();

        Template template = templateFactory.create("example", "index");
        template.set("mappings", CollectionUtils.collect(documents, new PathMapper(), new ArrayList()));
        template.set("newForm", new Location(FormPresenter.class, "documentId", Identity.NEW));

        return template;
    }

    private static class PathMapper implements Transformer {
        public Object transform(Object input) {
            Document document = (Document) input;
            return Pair.create(input, new Location(FormPresenter.class, "documentId", document.getIdentity()));
        }
    }
}
