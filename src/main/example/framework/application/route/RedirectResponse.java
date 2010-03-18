package example.framework.application.route;

import example.framework.Redirect;
import example.framework.Response;
import example.framework.ResponseContext;

import java.io.IOException;

public class RedirectResponse implements Response {

    private final Redirect redirect;

    public RedirectResponse(Redirect redirect) {
        this.redirect = redirect;
    }

    public void render(ResponseContext response) throws IOException {
        if (redirect != null) {
            response.setHeader(redirect);
            String url = createRedirectURL(response);
            response.sendRedirect(url);
        }
    }

    private String createRedirectURL(ResponseContext response) {
        String prefix = "";
        if (redirect.isContextRelative()) {
            prefix += response.getContextPath();
        }
        if (redirect.isServletRelative()) {
            prefix += response.getServletPath();
        }
        return prefix + redirect.getUrl();
    }
}
