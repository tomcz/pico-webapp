package example.framework;

import java.io.IOException;

public interface Response {
    void render(ResponseContext context) throws IOException;
}
