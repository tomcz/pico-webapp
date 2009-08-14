package example.framework.template;

import org.antlr.stringtemplate.NoIndentWriter;

import java.io.IOException;
import java.io.Writer;

public class WebTemplate implements Template {

    private final WebStringTemplate template;

    public WebTemplate(WebStringTemplate template) {
        this.template = template;
    }

    public void set(String name, Object value) {
        template.setAttribute(name, value);
    }

    public void setAggregate(String spec, Object... values) {
        template.setAggregate(spec, values);
    }

    public void setDefaultFormat(WebFormat format) {
        template.setDefaultFormat(format);
    }

    public void registerRenderer(Renderer renderer) {
        template.registerRenderer(renderer.getTypeToRender(), new RendererAdaptor(renderer));
    }

    public void write(Writer writer) throws IOException {
        template.write(new NoIndentWriter(writer));
    }
}
