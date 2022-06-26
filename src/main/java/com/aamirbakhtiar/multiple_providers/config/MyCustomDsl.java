package com.aamirbakhtiar.multiple_providers.config;

import com.aamirbakhtiar.multiple_providers.repository.OtpRepository;
import com.aamirbakhtiar.multiple_providers.security.filters.UsernamePasswordAuthFilter;
import com.aamirbakhtiar.multiple_providers.security.providers.OtpAuthProvider;
import com.aamirbakhtiar.multiple_providers.security.providers.UsernamePasswordAuthProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

    public static MyCustomDsl customDsl() {
        return new MyCustomDsl();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);

        UsernamePasswordAuthProvider usernamePasswordAuthProvider = context.getBean(UsernamePasswordAuthProvider.class);
        OtpAuthProvider otpAuthProvider = context.getBean(OtpAuthProvider.class);
        OtpRepository otpRepository = context.getBean(OtpRepository.class);

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.authenticationProvider(usernamePasswordAuthProvider).authenticationProvider(otpAuthProvider);

        http.addFilterAt(new UsernamePasswordAuthFilter(authenticationManager, otpRepository), BasicAuthenticationFilter.class);
    }
}
