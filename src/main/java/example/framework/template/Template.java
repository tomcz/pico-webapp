package example.framework.template;

import example.framework.Response;

import java.io.Writer;

public interface Template extends Response {

    void set(String name, Object value);

    void setContentType(String contentType);

    void setCharset(String charset);

    void write(Writer writer) throws Exception;
}
