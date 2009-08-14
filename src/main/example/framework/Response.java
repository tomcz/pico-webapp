package example.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Response {
    void render(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
