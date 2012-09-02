package example.framework.application;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class DefaultConfigurationTests {

    @Test
    public void shouldSubstituteSystemPropertiesAndTokensFromConfiguration() {
        Map<String, String> properties = newHashMap();
        properties.put("foo.path", "${user.dir}/${foo}");
        properties.put("foo", "bar");

        DefaultConfiguration configuration = new DefaultConfiguration(properties);
        String value = configuration.get("foo.path");

        assertThat(value, is(SystemUtils.USER_DIR + "/bar"));
    }

    @Test
    public void shouldReturnNullForUnknownKey() {
        Map<String, String> properties = newHashMap();

        DefaultConfiguration configuration = new DefaultConfiguration(properties);
        String value = configuration.get("foo");

        assertThat(value, nullValue());
    }
}