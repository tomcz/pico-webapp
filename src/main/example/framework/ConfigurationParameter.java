package example.framework;

import org.apache.commons.lang.Validate;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.NameBinding;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;
import org.picocontainer.parameters.AbstractParameter;
import org.picocontainer.parameters.ConstantParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Properties;

public class ConfigurationParameter extends AbstractParameter {

    private final String key;
    private final String defaultValue;

    public ConfigurationParameter(String key) {
        this(key, null);
    }

    public ConfigurationParameter(String key, String defaultValue) {
        this.defaultValue = defaultValue;
        this.key = key;
    }

    public Resolver resolve(PicoContainer container, ComponentAdapter<?> forAdapter, ComponentAdapter<?> injecteeAdapter,
                            Type expectedType, NameBinding expectedNameBinding, boolean useNames, Annotation binding) {

        ConstantParameter parameter = createConfiguredParameter(container);
        return parameter.resolve(container, forAdapter, injecteeAdapter, expectedType, expectedNameBinding, useNames, binding);
    }

    public void verify(PicoContainer container, ComponentAdapter<?> adapter, Type expectedType,
                       NameBinding expectedNameBinding, boolean useNames, Annotation binding) {

        ConstantParameter parameter = createConfiguredParameter(container);
        parameter.verify(container, adapter, expectedType, expectedNameBinding, useNames, binding);
    }

    public void accept(PicoVisitor visitor) {
        visitor.visitParameter(this);
    }

    private ConstantParameter createConfiguredParameter(PicoContainer container) {
        String value = getConfiguredProperty(container);
        return new ConstantParameter(value);
    }

    private String getConfiguredProperty(PicoContainer container) {
        Properties properties = container.getComponent(Properties.class);
        Validate.notNull(properties, "Cannot find configuration properties in container");
        String value = properties.getProperty(key, defaultValue);
        Validate.isTrue(value != null, "Cannot find configuration property for: ", key);
        return value;
    }
}
