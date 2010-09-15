package example.framework.template;

import example.framework.InputStreamSource;

public class WebTemplateFactory implements TemplateFactory {

    private final InputStreamSource source;
    private final String templateDir;

    public WebTemplateFactory(InputStreamSource source, String templateDir) {
        this.templateDir = templateDir;
        this.source = source;
    }

    public Template create(String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(templateDir, source);
        return createTemplate(templateName, group);
    }

    public Template create(String groupName, String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(groupName, templateDir, source);
        group.setSuperGroup(new WebStringTemplateGroup("shared", templateDir, source));
        return createTemplate(templateName, group);
    }

    private Template createTemplate(String templateName, WebStringTemplateGroup group) {
        return new WebTemplate(group.createTemplate(templateName));
    }
}
