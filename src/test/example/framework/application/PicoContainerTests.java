package example.framework.application;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

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
    public void shouldRetrieveObjectRegisteredByKeyAndType() {
        PicoContainer container = new PicoContainer();
        container.register("testList", ArrayList.class);

        List list1 = (List) container.get("testList");
        List list2 = (List) container.get("testList");

        assertThat(list2, sameInstance(list1));
    }

    @Test
    public void shouldRetrieveObjectRegisteredByKeyAndInstance() {
        PicoContainer container = new PicoContainer();
        container.registerInstance("testItem", "foo");

        String instance = (String) container.get("testItem");

        assertThat(instance, is("foo"));
    }

    @Test
    public void shouldCreateObjectUsingSelectedSingleValueConstructor() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, container.newArgs().constant("test"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("test"));
        assertThat(instance.getList(), is(Arrays.asList("default")));
    }

    @Test
    public void shouldCreateObjectUsingSelectedCollectionConstructor() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, container.newArgs().constant(Arrays.asList("test")));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("default"));
        assertThat(instance.getList(), is(Arrays.asList("test")));
    }

    @Test
    public void shouldCreateObjectUsingMixedAutowiringAndConstantValue() {
        PicoContainer container = new PicoContainer();

        container.registerInstances(Arrays.asList("test2"));
        container.register(TestObject.class, container.newArgs().constant("test1").autowired());

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("test1"));
        assertThat(instance.getList(), is(Arrays.asList("test2")));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenConfigurationPropertiesDontExist() {
        PicoContainer container = new PicoContainer();

        container.register(TestObject.class, container.newArgs().configuredProperty("key", "test"));

        container.get(TestObject.class);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenCannotResolveConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstances(new DefaultConfiguration(new HashMap<String, String>()));
        container.register(TestObject.class, container.newArgs().configuredProperty("key"));

        container.get(TestObject.class);
    }

    @Test
    public void shouldCreateObjectUsingDefaultConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstances(new DefaultConfiguration(new HashMap<String, String>()));
        container.register(TestObject.class, container.newArgs().configuredProperty("key", "test"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("test"));
    }

    @Test
    public void shouldCreateObjectUsingAvailableConfigurationProperty() {
        PicoContainer container = new PicoContainer();

        container.registerInstances(new DefaultConfiguration(Collections.singletonMap("key", "test")));
        container.register(TestObject.class, container.newArgs().configuredProperty("key"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("test"));
    }

    @Test
    public void shouldCreateObjectUsingAutowiredParameterKey() {
        PicoContainer container = new PicoContainer();

        container.registerInstance("testKey", "test");
        container.register(TestObject.class, container.newArgs().autowired("testKey"));

        TestObject instance = container.get(TestObject.class);

        assertThat(instance.getValue(), is("test"));
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
