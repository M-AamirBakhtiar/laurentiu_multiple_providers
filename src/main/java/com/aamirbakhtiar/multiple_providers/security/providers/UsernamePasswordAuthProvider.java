package com.aamirbakhtiar.multiple_providers.security.providers;

import com.aamirbakhtiar.multiple_providers.security.authentications.UsernamePasswordAuthentication;
import com.aamirbakhtiar.multiple_providers.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {


    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthProvider(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = customUserDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthentication(username, password, user.getAuthorities());
        }

        throw new BadCredentialsException("username or password is incorrect.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
