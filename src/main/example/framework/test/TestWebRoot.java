package example.framework.test;

import example.framework.WebRoot;
import org.apache.commons.lang.UnhandledException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TestWebRoot implements WebRoot {

    private final File root;

    public TestWebRoot(File root) {
        this.root = root;
    }

    public String getUrlTo(String resource) {
        try {
            URL url = root.toURI().toURL();
            return url.toExternalForm() + resource;

        } catch (MalformedURLException e) {
            throw new UnhandledException(e);
        }
    }
}
