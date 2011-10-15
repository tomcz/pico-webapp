package example.framework.template;

import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;

import javax.servlet.ServletContext;

public class FreemarkerTemplateFactory implements TemplateFactory {

    private final Configuration config;

    public FreemarkerTemplateFactory(ServletContext context) {
        this(new WebappTemplateLoader(context, "/WEB-INF/templates"));
    }

    // for tests
    protected FreemarkerTemplateFactory(TemplateLoader templateLoader) {
        config = new Configuration();
        config.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        config.setTemplateLoader(new HtmlTemplateLoader(templateLoader));
    }

    @Override
    public Template create(String groupName, String templateName) {
        String templatePath = groupName + "/" + templateName + ".ftl";
        try {
            return new FreemarkerTemplate(config.getTemplate(templatePath));
        } catch (Exception e) {
            throw new TemplateException(templatePath, e);
        }
    }
}
