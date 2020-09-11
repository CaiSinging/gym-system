package cn.edu.nwafu.cie.se2019.gym.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class GuestToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;

    public GuestToken(Object principal, Object credentials) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST")));
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public GuestToken(Object principal, Object credentials,
                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
