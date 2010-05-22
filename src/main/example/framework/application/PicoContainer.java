package example.framework.application;

import example.framework.Container;
import example.framework.ContainerArguments;
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

    private PicoContainer(MutablePicoContainer parent) {
        container = new TransientPicoContainer(parent);
    }

    public void register(Class type) {
        container.addComponent(type);
    }

    public void register(Object key, Class type) {
        container.addComponent(key, type);
    }

    public void register(Class type, ContainerArguments args) {
        Parameter[] parameters = parameters(args);
        if (parameters.length > 0) {
            container.addComponent(type, type, parameters);
        } else {
            container.addComponent(type);
        }
    }

    public void register(Object key, Class type, ContainerArguments args) {
        Parameter[] parameters = parameters(args);
        if (parameters.length > 0) {
            container.addComponent(key, type, parameters);
        } else {
            container.addComponent(key, type);
        }
    }

    private Parameter[] parameters(ContainerArguments args) {
        Validate.isTrue(args instanceof PicoContainerArguments, "Expected PicoContainerArguments, got ", args);
        PicoContainerArguments picoArgs = (PicoContainerArguments) args;
        return picoArgs.params();
    }

    public void registerInstance(Object key, Object instance) {
        container.addComponent(key, instance);
    }

    public void registerInstances(Object... instances) {
        for (Object instance : instances) {
            container.addComponent(instance);
        }
    }

    public Object get(Object key) {
        Object instance = container.getComponent(key);
        Validate.isTrue(instance != null, "Cannot find instance for key ", key);
        return instance;
    }

    public <T> T get(Class<T> type) {
        T instance = container.getComponent(type);
        Validate.isTrue(instance != null, "Cannot find instance of ", type);
        return instance;
    }

    public ContainerArguments newArgs() {
        return new PicoContainerArguments();
    }

    public Container newChild() {
        return new PicoContainer(container);
    }

    public void dispose() {
        container.dispose();
    }
}
