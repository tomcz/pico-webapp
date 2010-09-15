package example.framework.template;

import example.framework.InputStreamSource;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;

public class WebStringTemplateGroup extends StringTemplateGroup {

    private final Logger logger = Logger.getLogger(getClass());

    private final InputStreamSource source;

    public WebStringTemplateGroup(String rootDir, InputStreamSource source) {
        super("default", rootDir);
        this.source = source;
    }

    public WebStringTemplateGroup(String name, String rootDir, InputStreamSource source) {
        super(name, rootDir + "/" + name);
        this.source = source;
    }

    public WebStringTemplate createTemplate(String templateName) {
        return (WebStringTemplate) getInstanceOf(templateName);
    }

    public StringTemplate createStringTemplate() {
        return new WebStringTemplate();
    }

    @Override
    protected StringTemplate loadTemplate(String name, String fileName) {
        InputStream stream = null;
        BufferedReader reader = null;
        try {
            stream = source.getStream(fileName);
            reader = new BufferedReader(getInputStreamReader(stream));
            return loadTemplate(name, reader);

        } catch (Exception e) {
            logger.debug("Cannot load template from " + fileName + " - cause is: " + e);
            return null;

        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(stream);
        }
    }
}
