package example.framework.template;

import example.framework.Response;

import java.io.IOException;
import java.io.Writer;

public interface Template extends Response {

    void set(String name, Object value);

    void setAggregate(String spec, Object... values);

    void setContentType(String contentType);

    void setCharset(String charset);

    void setDefaultFormat(WebFormat format);

    void registerRenderer(Renderer renderer);

    void write(Writer writer) throws IOException;
}
