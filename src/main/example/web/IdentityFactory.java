package example.web;

import example.domain.Identity;

public interface IdentityFactory {
    Identity createFrom(String value);
}
