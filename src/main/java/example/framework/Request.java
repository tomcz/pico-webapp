package example.framework;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;

public interface Request {

    String getParameter(String name);

    List<String> getParameters(String name);

    String getPathVariable(String name);

    Identity getIdentity(String name);

    Cookie getCookie(String name);

    String getCookieValue(String name);

    String getRequestBodyText();

    InputStream getRequestBodyStream();
}
