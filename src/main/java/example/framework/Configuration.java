package example.framework;

import com.google.common.base.Optional;

public interface Configuration {
    Optional<String> get(String key);
}
