package example.framework.servlet;

import example.framework.InputStreamSource;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

public class ServletInputStreamSource implements InputStreamSource {

    private final ServletContext context;

    public ServletInputStreamSource(ServletContext context) {
        this.context = context;
    }

    public InputStream getStream(String path) throws IOException {
        return context.getResourceAsStream(path);
    }
}
