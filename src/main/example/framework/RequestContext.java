package example.framework;

import javax.servlet.http.Cookie;

public interface RequestContext {

    String getLookupPath();

    RequestMethod getMethod();

    String getParameter(String name);

    String[] getParameterValues(String name);

    Cookie[] getCookies();
}
