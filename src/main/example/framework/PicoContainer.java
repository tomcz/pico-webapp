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
            register(type, type, arguments);
        } else {
            container.addComponent(type);
        }
    }

    public void register(Object key, Class type, ConstructorArgument... arguments) {
        Validate.notNull(type, "Cannot register null type");
        Validate.notNull(key, "Cannot register null key");
        if (arguments.length > 0) {
            container.addComponent(key, type, parameters(arguments));
        } else {
            container.addComponent(key, type);
        }
    }

    private Parameter[] parameters(ConstructorArgument[] arguments) {
        Parameter[] parameters = new Parameter[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            parameters[i] = arguments[i].parameter();
        }
        return parameters;
    }

    public void registerInstance(Object key, Object instance) {
        Validate.notNull(instance, "Cannot register null instance");
        Validate.notNull(key, "Cannot register null key");
        container.addComponent(key, instance);
    }

    public void registerInstance(Object instance) {
        Validate.notNull(instance, "Cannot register null instance");
        container.addComponent(instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T getForKey(Object key) {
        T instance = (T) container.getComponent(key);
        Validate.isTrue(instance != null, "Cannot find instance for ", key);
        return instance;
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
