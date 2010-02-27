package example.framework.template;

import org.antlr.stringtemplate.AttributeRenderer;
import org.antlr.stringtemplate.NoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;

import java.io.IOException;
import java.io.Writer;

public class WebStringTemplate extends StringTemplate {

    private WebFormat defaultFormat = WebFormat.HTML;

    public void setDefaultFormat(WebFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public void setAggregate(String aggrSpec, Object... values) {
        super.setAttribute(aggrSpec, values);
    }

    @Override
    public AttributeRenderer getAttributeRenderer(Class aClass) {
        AttributeRenderer renderer = super.getAttributeRenderer(aClass);
        return (renderer != null) ? renderer : new WebAttributeRenderer(defaultFormat);
    }

    public void write(Writer writer) throws IOException {
        write(new NoIndentWriter(writer));
    }
}
