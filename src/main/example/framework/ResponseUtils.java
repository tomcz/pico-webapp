package example.framework;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

    public static void addHeaderToResponse(HttpServletResponse response, Header header) {
        addFields(response, header.getFields());
        addCookies(response, header.getCookies());
    }

    private static void addFields(HttpServletResponse response, HeaderFields fields) {
        if (fields != null) {
            fields.addTo(response);
        }
    }

    private static void addCookies(HttpServletResponse response, Cookies cookies) {
        if (cookies != null) {
            cookies.addTo(response);
        }
    }
}
