package example.framework;

import org.apache.commons.lang.Validate;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.containers.TransientPicoContainer;

public class PicoContainer implements Container {

    private final MutablePicoContainer container;

    public PicoContainer() {
        container = new DefaultPicoContainer(new Caching());
    }

    public PicoContainer(PicoContainer parent) {
        container = new TransientPicoContainer(parent.container);
    }

    public void register(Class type, ConstructorArgument... arguments) {
        Validate.notNull(type, "Cannot register null type");
        if (arguments.length > 0) {
            container.addComponent(type, type, parameters(arguments));
        } else {
            container.addComponent(type);
        }
    }

    private Parameter[] parameters(ConstructorArgument[] arguments) {
        Parameter[] parameters = new Parameter[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            parameters[i] = arguments[i].parameter();
        }
        return parameters;
    }

    public void registerInstance(Object instance) {
        Validate.notNull(instance, "Cannot register null instance");
        container.addComponent(instance);
    }

    public <T> T get(Class<T> type) {
        T instance = container.getComponent(type);
        Validate.isTrue(instance != null, "Cannot find instance of ", type);
        return instance;
    }

    public void dispose() {
        container.dispose();
    }
}
