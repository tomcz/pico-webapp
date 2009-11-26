package example.framework;

import javax.servlet.http.Cookie;
import java.util.List;

public interface RequestContext {

    String getLookupPath();

    RequestMethod getMethod();

    String getParameter(String name);

    List<String> getParameterValues(String name);

    List<Cookie> getCookies();
}
