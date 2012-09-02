package example.framework;

import com.google.common.base.Optional;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.util.List;

public interface Request {

    String getParameter(String name);

    List<String> getParameters(String name);

    String getPathVariable(String name);

    Identity getIdentity(String name);

    Optional<Cookie> getCookie(String name);

    Optional<String> getCookieValue(String name);

    String getRequestBodyText();

    InputStream getRequestBodyStream();
}
