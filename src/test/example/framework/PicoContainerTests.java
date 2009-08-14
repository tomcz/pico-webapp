package example.framework;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
