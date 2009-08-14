package example.framework;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectResponse implements Response {

    private final Redirect redirect;

    public RedirectResponse(Redirect redirect) {
        this.redirect = redirect;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (redirect != null) {
            addCookiesToResponse(response);
            String url = createRedirectURL(request);
            response.sendRedirect(url);
        }
    }

    private void addCookiesToResponse(HttpServletResponse response) {
        for (Cookie cookie : redirect.getCookies()) {
            response.addCookie(cookie);
        }
    }

    private String createRedirectURL(HttpServletRequest request) {
        String prefix = "";
        if (redirect.isContextRelative()) {
            prefix += request.getContextPath();
        }
        if (redirect.isServletRelative()) {
            prefix += request.getServletPath();
        }
        return prefix + redirect.getUrl();
    }
}
