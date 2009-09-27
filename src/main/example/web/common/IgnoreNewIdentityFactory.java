package example.web.common;

import example.framework.Identity;
import example.framework.IdentityFactory;

public class IgnoreNewIdentityFactory implements IdentityFactory {
    public Identity createFrom(String value) {
        return Identity.fromValue(value);
    }
}
