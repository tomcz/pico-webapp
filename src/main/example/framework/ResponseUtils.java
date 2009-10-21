package example.framework;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

    public static void addHeadersToResponse(HttpServletResponse response, Headers headers) {
        if (headers != null) {
            headers.addTo(response);
        }
    }

    public static void addCookiesToResponse(HttpServletResponse response, Cookies cookies) {
        if (cookies != null) {
            cookies.addTo(response);
        }
    }
}
