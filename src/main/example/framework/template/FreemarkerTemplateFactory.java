package example.framework.template;

import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.log.Logger;
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
        configureFreemarkerLogger();
        config = new Configuration();
        config.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        config.setTemplateLoader(new HtmlTemplateLoader(templateLoader));
        config.setTemplateExceptionHandler(new HtmlExceptionHandler());
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

    private static void configureFreemarkerLogger() {
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_SLF4J);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
