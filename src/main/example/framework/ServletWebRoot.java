package example.framework;

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

    public String getUrlTo(String directory) {
        try {
            URL resource = context.getResource(directory);
            Validate.notNull(resource, "Cannot find path to " + directory);
            return resource.toExternalForm();

        } catch (MalformedURLException e) {
            throw new UnhandledException(e);
        }
    }
}
