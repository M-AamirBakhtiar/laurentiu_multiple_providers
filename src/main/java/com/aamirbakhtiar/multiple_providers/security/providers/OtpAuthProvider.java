package com.aamirbakhtiar.multiple_providers.security.providers;

import com.aamirbakhtiar.multiple_providers.repository.OtpRepository;
import com.aamirbakhtiar.multiple_providers.security.authentications.OtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthProvider implements AuthenticationProvider {


    private final OtpRepository otpRepository;

    public OtpAuthProvider(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        var o = otpRepository.findOtpByUsername(username);

        if (o.isPresent()) {
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        }

        throw new BadCredentialsException("otp is incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
