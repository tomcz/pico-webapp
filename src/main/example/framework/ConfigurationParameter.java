package example.framework;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.NameBinding;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;
import org.picocontainer.parameters.AbstractParameter;
import org.picocontainer.parameters.ConstantParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

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
                            Type expectedType, NameBinding nameBinding, boolean useNames, Annotation binding) {

        ConstantParameter parameter = createConfiguredParameter(container);
        return parameter.resolve(container, forAdapter, injecteeAdapter, expectedType, nameBinding, useNames, binding);
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
        Configuration configuration = container.getComponent(Configuration.class);
        if (configuration == null) {
            throw new IllegalStateException("Cannot find Configuration instance in container");
        }
        String value = configuration.get(key);
        if (value == null) {
            value = defaultValue;
        }
        if (value == null) {
            throw new IllegalStateException("Cannot find configured value for key [" + key + "]");
        }
        return value;
    }
}
