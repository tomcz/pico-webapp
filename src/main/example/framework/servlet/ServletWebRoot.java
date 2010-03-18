package example.framework.servlet;

import example.framework.WebRoot;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import javax.servlet.ServletContext;
import java.net.MalformedURLException;
import java.net.URL;

public class ServletWebRoot implements WebRoot {

    private final ServletContext context;

    public ServletWebRoot(ServletContext context) {
        this.context = context;
    }

    public String getUrlTo(String resource) {
        try {
            URL url = context.getResource(resource);
            Validate.notNull(url, "Cannot find path to " + resource);
            return url.toExternalForm();

        } catch (MalformedURLException e) {
            throw new UnhandledException(e);
        }
    }
}
