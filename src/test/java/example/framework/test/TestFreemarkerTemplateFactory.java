package example.framework.test;

import example.framework.template.FreemarkerTemplateFactory;
import freemarker.cache.FileTemplateLoader;

import java.io.File;
import java.io.IOException;

public class TestFreemarkerTemplateFactory extends FreemarkerTemplateFactory {

    public TestFreemarkerTemplateFactory() {
        super(templateLoader());
    }

    private static FileTemplateLoader templateLoader() {
        try {
            return new FileTemplateLoader(new File("src/main/webapp/WEB-INF/templates"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
