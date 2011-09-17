package example.domain.web;

import ch.lambdaj.function.convert.Converter;
import example.domain.Document;
import example.domain.Document.Field;
import example.domain.DocumentRepository;
import example.domain.Property;
import example.framework.Identity;
import example.framework.Location;
import example.framework.Presenter;
import example.framework.Request;
import example.framework.Response;
import example.framework.RouteMapping;
import example.framework.template.Template;
import example.framework.template.TemplateFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import static ch.lambdaj.Lambda.convert;
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
        Document document = repository.get(documentId);

        Template template = templateFactory.create("example", "form");
        template.set("indexLink", new Location(IndexPresenter.class));
        template.set("document", createDocumentModel(document));
        template.set("fieldOptions", options(document, Field.two));

        return template;
    }

    private List<Option> options(Document document, Field field) {
        final Property property = document.get(field);
        List<String> values = Arrays.asList("", "option1", "option2", "error");
        return convert(values, new Converter<String, Option>() {
            public Option convert(String value) {
                boolean selected = StringUtils.equals(property.getValue(), value);
                return new Option(value, selected);
            }
        });
    }

    public static class Option {

        private final String value;
        private final boolean selected;

        public Option(String value, boolean selected) {
            this.selected = selected;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
