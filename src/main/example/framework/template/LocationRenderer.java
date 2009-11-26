package example.framework.template;

import example.framework.Location;
import example.framework.ResponseContext;

public class LocationRenderer implements Renderer {

    private final ResponseContext context;

    public LocationRenderer(ResponseContext context) {
        this.context = context;
    }

    public Class getTypeToRender() {
        return Location.class;
    }

    public String toString(Object obj) {
        return WebFormat.HTML.format(createURL((Location) obj));
    }

    public String toString(Object obj, String formatName) {
        return WebFormat.fromName(formatName).format(createURL((Location) obj));
    }

    private String createURL(Location location) {
        String url = "";
        if (location.isContextRelative()) {
            url += context.getContextPath();
        }
        if (location.isServletRelative()) {
            url += context.getServletPath();
        }
        return url + location.getUrl();
    }
}
