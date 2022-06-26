package com.aamirbakhtiar.multiple_providers.security.filters;

import com.aamirbakhtiar.multiple_providers.security.authentications.TokenAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // get token from request header
        var token = request.getHeader("Authorization");

        // create a new Authentication Type / Object
        Authentication authentication = new TokenAuthentication(token, null);

        // pass the Authentication Type / Object so the Manager can choose correct Authentication Provider
        var a = authenticationManager.authenticate(authentication);

        // Store the fully Authenticated Authentication Object in the Security Context
        SecurityContextHolder.getContext().setAuthentication(a);

        // Pass the Request to the Rest of the Filter Chain
        filterChain.doFilter(request, response);

        

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
