package example.framework.application;

import example.framework.ContainerArguments;
import org.picocontainer.Parameter;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import java.util.List;

import static example.utils.Generics.newArrayList;

public class PicoContainerArguments implements ContainerArguments {

    private List<Parameter> params = newArrayList();

    public ContainerArguments autowired() {
        params.add(new ComponentParameter());
        return this;
    }

    public ContainerArguments autowired(Object key) {
        params.add(new ComponentParameter(key));
        return this;
    }

    public ContainerArguments constant(Object value) {
        params.add(new ConstantParameter(value));
        return this;
    }

    public ContainerArguments configuredProperty(String key) {
        params.add(new ConfigurationParameter(key));
        return this;
    }

    public ContainerArguments configuredProperty(String key, String defaultValue) {
        params.add(new ConfigurationParameter(key, defaultValue));
        return this;
    }

    public Parameter[] params() {
        return params.toArray(new Parameter[params.size()]);
    }
}
