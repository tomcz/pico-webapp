package example.framework;

public interface Container {

    void register(Class type);

    void register(Object key, Class type);

    void register(Class type, ContainerArguments args);

    void register(Object key, Class type, ContainerArguments args);

    void registerInstance(Object key, Object instance);

    void registerInstances(Object... instances);

    Object get(Object key);

    <T> T get(Class<T> type);

    ContainerArguments newArgs();

    Container newChild();

    void dispose();
}
