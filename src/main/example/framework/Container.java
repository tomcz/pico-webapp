package example.framework;

public interface Container {

    void register(Class type, ConstructorArgument... arguments);

    void registerInstance(Object instance);

    <T> T get(Class<T> type);
}
