package example.framework.template;

import org.apache.commons.lang.Validate;

import javax.servlet.ServletContext;

public class WebTemplateFactory implements TemplateFactory {

    private final String templateRoot;

    public WebTemplateFactory(ServletContext context) {
        this(context, "/templates");
    }

    public WebTemplateFactory(ServletContext context, String rootDir) {
        templateRoot = context.getRealPath(rootDir);
        Validate.notEmpty(templateRoot, "Cannot find real path to " + rootDir);
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
