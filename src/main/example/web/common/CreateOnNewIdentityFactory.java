package example.web.common;

import example.domain.Identity;
import example.web.IdentityFactory;

public class CreateOnNewIdentityFactory implements IdentityFactory {
    public Identity createFrom(String value) {
        Identity identity = Identity.fromValue(value);
        return identity.isNew() ? new Identity() : identity;
    }
}
