package example.framework.template;

import example.framework.WebRoot;

public class WebTemplateFactory implements TemplateFactory {

    private final String templateRoot;

    public WebTemplateFactory(WebRoot root, String templateDir) {
        templateRoot = root.getUrlTo(templateDir);
    }

    public Template create(String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(templateRoot);
        return new WebTemplate((WebStringTemplate) group.getInstanceOf(templateName));
    }

    public Template create(String groupName, String templateName) {
        WebStringTemplateGroup group = new WebStringTemplateGroup(groupName, templateRoot);
        group.setSuperGroup(new WebStringTemplateGroup("shared", templateRoot));
        return new WebTemplate((WebStringTemplate) group.getInstanceOf(templateName));
    }
}
