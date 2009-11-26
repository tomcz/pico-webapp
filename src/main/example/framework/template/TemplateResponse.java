package example.framework.template;

import example.framework.Response;
import example.framework.ResponseContext;

import java.io.IOException;

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

    public void render(ResponseContext response) throws IOException {
        response.setContentType(contentType);
        response.setCharacterEncoding(charset);

        template.set("base", response.getContextPath());
        template.set("request", response.getAttributes());
        template.registerRenderer(new LocationRenderer(response));

        template.write(response.getWriter());
    }
}
