package example.framework.template;

import example.framework.Response;
import example.utils.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class TemplateResponse implements Response {

    private final Template template;
    private final String contentType;
    private final String charset;

    public TemplateResponse(Template template) {
        this(template, "text/html", "UTF-8");
    }

    public TemplateResponse(Template template, String contentType, String charset) {
        this.template = template;
        this.contentType = contentType;
        this.charset = charset;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType(contentType + ";charset=" + charset);
        response.setCharacterEncoding(charset);

        template.set("base", request.getContextPath());
        template.set("request", createMapOfRequestAttributes(request));
        template.registerRenderer(new LocationRenderer(request));

        template.write(response.getWriter());
    }

    private Map<String, Object> createMapOfRequestAttributes(HttpServletRequest request) {
        Map<String, Object> attributes = Maps.create();
        for (Enumeration names = request.getAttributeNames(); names.hasMoreElements();) {
            String name = (String) names.nextElement();
            attributes.put(name, request.getAttribute(name));
        }
        return attributes;
    }
}
