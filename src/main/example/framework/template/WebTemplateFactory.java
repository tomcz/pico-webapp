package example.framework.template;

import org.apache.commons.lang.Validate;

import javax.servlet.ServletContext;
import java.net.URL;

public class WebTemplateFactory implements TemplateFactory {

    private final String templateRoot;

    public WebTemplateFactory(ServletContext context, String rootDir) throws Exception {
        URL url = context.getResource(rootDir);
        Validate.notNull(url, "Cannot find " + rootDir);
        templateRoot = url.toExternalForm();
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
