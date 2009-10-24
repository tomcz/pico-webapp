package example.framework;

public interface Container {

    void register(Object key, Class type, ConstructorArgument... arguments);

    void register(Class type, ConstructorArgument... arguments);

    void registerInstance(Object key, Object instance);

    void registerInstance(Object instance);

    Object getForKey(Object key);

    <T> T get(Class<T> type);
}
