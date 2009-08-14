package example.framework;

public interface Container {

    void register(Class type);

    void registerInstance(Object instance);

    <T> T get(Class<T> type);

    void dispose();
}
