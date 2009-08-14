package example.framework.template;

import example.framework.Location;

import javax.servlet.http.HttpServletRequest;

public class LocationRenderer implements Renderer {

    private final HttpServletRequest request;

    public LocationRenderer(HttpServletRequest request) {
        this.request = request;
    }

    public Class getTypeToRender() {
        return Location.class;
    }

    public String toString(Object obj) {
        return createURL((Location) obj);
    }

    public String toString(Object obj, String formatName) {
        return WebFormat.fromName(formatName).format(createURL((Location) obj));
    }

    private String createURL(Location location) {
        String url = "";
        if (location.isContextRelative()) {
            url += request.getContextPath();
        }
        if (location.isServletRelative()) {
            url += request.getServletPath();
        }
        return url + location.getUrl();
    }
}
