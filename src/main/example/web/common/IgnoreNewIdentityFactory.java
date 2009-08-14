package example.web.common;

import example.domain.Identity;
import example.web.IdentityFactory;

public class IgnoreNewIdentityFactory implements IdentityFactory {
    public Identity createFrom(String value) {
        return Identity.fromValue(value);
    }
}
