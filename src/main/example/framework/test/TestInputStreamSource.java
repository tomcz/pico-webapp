package example.framework.test;

import example.framework.InputStreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestInputStreamSource implements InputStreamSource {

    public InputStream getStream(String path) throws IOException {
        return new FileInputStream(new File("web", path));
    }
}
