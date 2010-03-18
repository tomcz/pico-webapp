package example.framework;

public interface ContainerArguments {

    ContainerArguments autowired();

    ContainerArguments autowired(Object key);

    ContainerArguments constant(Object value);

    ContainerArguments configuredProperty(String key);

    ContainerArguments configuredProperty(String key, String defaultValue);
}
