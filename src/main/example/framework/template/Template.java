package example.framework.template;

import java.io.IOException;
import java.io.Writer;

public interface Template {

    void set(String name, Object value);

    void setAggregate(String spec, Object... values);

    void setDefaultFormat(WebFormat format);

    void registerRenderer(Renderer renderer);

    void write(Writer writer) throws IOException;
}
