package example.framework;

import static example.framework.ResponseUtils.addCookiesToResponse;
import static example.framework.ResponseUtils.addHeadersToResponse;

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
            addHeadersToResponse(response, redirect.getHeaders());
            addCookiesToResponse(response, redirect.getCookies());
            String url = createRedirectURL(request);
            response.sendRedirect(url);
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
