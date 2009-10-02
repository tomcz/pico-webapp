package example.framework;

import org.picocontainer.Parameter;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

public class ConstructorArgument {

    private final Parameter parameter;

    private ConstructorArgument(Parameter parameter) {
        this.parameter = parameter;
    }

    public static ConstructorArgument autowired() {
        return new ConstructorArgument(new ComponentParameter());
    }

    public static ConstructorArgument constant(Object value) {
        return new ConstructorArgument(new ConstantParameter(value));
    }

    public static ConstructorArgument configuredProperty(String key) {
        return new ConstructorArgument(new ConfigurationParameter(key));
    }

    public static ConstructorArgument configuredProperty(String key, String defaultValue) {
        return new ConstructorArgument(new ConfigurationParameter(key, defaultValue));
    }

    public Parameter parameter() {
        return parameter;
    }
}
