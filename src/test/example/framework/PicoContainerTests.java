package example.framework;

import static example.framework.ConstructorArgument.autowired;
import static example.framework.ConstructorArgument.configuredProperty;
import static example.framework.ConstructorArgument.constant;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PicoContainerTests {

    @Test
    public void parentContainerShouldReturnTheSameInstanceWhenAskedTwice() {
        PicoContainer container = new PicoContainer();
        container.register(ArrayList.class);

        List list1 = container.get(List.class);
        List list2 = container.get(List.class);

        assertThat(list2, sameInstance(list1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCannotFindInstance() {
        PicoContainer container = new PicoContainer();
        container.get(List.class);
    }

    @Test
    public void shouldCreateObjectUsingSelectedSingleValueConstructor() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, constant("test"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), equalTo("test"));
        assertThat(instance.getList(), equalTo(Arrays.asList("default")));
    }

    @Test
    public void shouldCreateObjectUsingSelectedCollectionConstructor() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, constant(Arrays.asList("test")));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), equalTo("default"));
        assertThat(instance.getList(), equalTo(Arrays.asList("test")));
    }

    @Test
    public void shouldCreateObjectUsingMixedAutowiringAndConstantValue() {
        PicoContainer container = new PicoContainer();

        container.registerInstance(Arrays.asList("test2"));
        container.register(TestObject.class, constant("test1"), autowired());

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), equalTo("test1"));
        assertThat(instance.getList(), equalTo(Arrays.asList("test2")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenConfigurationPropertiesDontExist() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, configuredProperty("key", "test"));

        container.get(TestObject.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCannotResolveConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstance(new Properties());
        container.register(TestObject.class, configuredProperty("key"));

        container.get(TestObject.class);
    }

    @Test
    public void shouldCreateObjectUsingDefaultConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstance(new Properties());
        container.register(TestObject.class, configuredProperty("key", "test"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), equalTo("test"));
    }

    @Test
    public void shouldCreateObjectUsingAvailableConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstance(properties("key", "test"));
        container.register(TestObject.class, configuredProperty("key"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), equalTo("test"));
    }

    private Properties properties(String key, String value) {
        Properties properties = new Properties();
        properties.setProperty(key, value);
        return properties;
    }

    public static class TestObject {

        private final String value;
        private final List<String> list;

        public TestObject(String value) {
            this(value, Arrays.asList("default"));
        }

        public TestObject(List<String> list) {
            this("default", list);
        }

        public TestObject(String value, List<String> list) {
            this.value = value;
            this.list = list;
        }

        public String getValue() {
            return value;
        }

        public List<String> getList() {
            return list;
        }
    }
}
