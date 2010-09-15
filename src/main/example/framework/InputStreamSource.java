package example.framework;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {

    InputStream getStream(String path) throws IOException;
}
