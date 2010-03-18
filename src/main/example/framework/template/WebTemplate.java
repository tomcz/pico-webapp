package example.framework.template;

import example.framework.ResponseContext;

import java.io.IOException;
import java.io.Writer;

public class WebTemplate implements Template {

    public static final String DEFAULT_CONTENT_TYPE = "text/html";
    public static final String DEFAULT_CHARSET = "UTF-8";

    private final WebStringTemplate template;

    private String contentType = DEFAULT_CONTENT_TYPE;
    private String charset = DEFAULT_CHARSET;

    public WebTemplate(WebStringTemplate template) {
        this.template = template;
    }

    public void set(String name, Object value) {
        template.setAttribute(name, value);
    }

    public void setAggregate(String spec, Object... values) {
        template.setAggregate(spec, values);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setDefaultFormat(WebFormat format) {
        template.setDefaultFormat(format);
    }

    public void registerRenderer(Renderer renderer) {
        template.registerRenderer(renderer.getTypeToRender(), new RendererAdaptor(renderer));
    }

    public void write(Writer writer) throws IOException {
        template.write(writer);
    }

    public void render(ResponseContext response) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding(charset);

        set("base", response.getContextPath());
        set("request", response.getAttributes());
        registerRenderer(new LocationRenderer(response));

        write(response.getWriter());
    }
}
