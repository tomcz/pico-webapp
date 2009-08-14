package example.framework;

import javax.servlet.http.HttpServletRequest;

public interface Application {

    Response process(HttpServletRequest servletRequest, RequestMethod method);

    void dispose();
}
