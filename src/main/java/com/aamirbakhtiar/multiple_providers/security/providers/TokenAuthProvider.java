package com.aamirbakhtiar.multiple_providers.security.providers;

import com.aamirbakhtiar.multiple_providers.security.authentications.TokenAuthentication;
import com.aamirbakhtiar.multiple_providers.security.managers.TokenManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    private final TokenManager tokenManager;

    public TokenAuthProvider(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        boolean exits = tokenManager.contains(token);

        if(exits) {
            return new TokenAuthentication(token, null, null);
        }

        throw new BadCredentialsException("token was not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
