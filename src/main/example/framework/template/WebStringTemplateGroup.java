package example.framework.template;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class WebStringTemplateGroup extends StringTemplateGroup {

    public WebStringTemplateGroup(String rootDir) {
        super("default", rootDir);
    }

    public WebStringTemplateGroup(String name, String rootDir) {
        super(name, rootDir + "/" + name);
    }

    @Override
    public StringTemplate createStringTemplate() {
        return new WebStringTemplate();
    }
}
