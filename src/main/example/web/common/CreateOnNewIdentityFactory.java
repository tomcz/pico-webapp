package example.web.common;

import example.framework.Identity;
import example.framework.IdentityFactory;

public class CreateOnNewIdentityFactory implements IdentityFactory {
    public Identity createFrom(String value) {
        Identity identity = Identity.fromValue(value);
        return identity.isNew() ? new Identity() : identity;
    }
}
