package example.framework.application;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import example.framework.Configuration;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class DefaultConfiguration implements Configuration {

    private final StrSubstitutor substitutor;
    private final Map<String, String> properties;

    public DefaultConfiguration(Map<String, String> properties) {
        Map tokens = createTokensToSubstitute(properties);
        this.substitutor = new StrSubstitutor(tokens);
        this.properties = properties;
    }

    private static Map createTokensToSubstitute(Map<String, String> properties) {
        Map tokens = new HashMap(System.getProperties());
        tokens.putAll(properties);
        return tokens;
    }

    public Optional<String> get(String key) {
        Optional<String> optional = Optional.fromNullable(properties.get(key));
        return optional.transform(new Function<String, String>() {
            public String apply(String value) {
                return substitutor.replace(value).trim();
            }
        });
    }
}
