package example.framework.template;

import example.framework.ResponseContext;
import example.utils.Generics;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreemarkerTemplate implements Template {

    private String contentType = "text/html";
    private String charset = "UTF-8";

    private final freemarker.template.Template template;
    private final Map<String, Object> model;

    public FreemarkerTemplate(freemarker.template.Template template) {
        this.model = Generics.newHashMap();
        this.template = template;
    }

    @Override
    public void set(String name, Object value) {
        model.put(name, value);
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public void write(Writer writer) throws IOException {
        try {
            template.process(model, writer);
        } catch (freemarker.template.TemplateException e) {
            throw new TemplateException("Cannot write out template", e);
        }
    }

    @Override
    public void render(ResponseContext context) throws IOException {
        context.setContentType(contentType);
        context.setCharacterEncoding(charset);
        model.putAll(context.getAttributes());
        write(context.getWriter());
    }
}
