package example.framework.template;

import com.google.common.collect.Maps;
import example.framework.ResponseContext;

import java.io.Writer;
import java.util.Map;

public class FreemarkerTemplate implements Template {

    private String contentType = "text/html";
    private String charset = "UTF-8";

    private final freemarker.template.Template template;
    private final Map<String, Object> model;

    public FreemarkerTemplate(freemarker.template.Template template) {
        this.model = Maps.newHashMap();
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
    public void write(Writer writer) throws Exception {
        template.process(model, writer);
    }

    @Override
    public void render(ResponseContext context) throws Exception {
        context.setContentType(contentType);
        context.setCharacterEncoding(charset);
        model.putAll(context.getAttributes());
        write(context.getWriter());
    }
}
