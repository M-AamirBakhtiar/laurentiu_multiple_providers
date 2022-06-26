package com.aamirbakhtiar.multiple_providers.security.filters;

import com.aamirbakhtiar.multiple_providers.entity.Otp;
import com.aamirbakhtiar.multiple_providers.repository.OtpRepository;
import com.aamirbakhtiar.multiple_providers.security.authentications.OtpAuthentication;
import com.aamirbakhtiar.multiple_providers.security.authentications.UsernamePasswordAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;


public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;

    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager, OtpRepository otpRepository) {
        this.authenticationManager = authenticationManager;
        this.otpRepository = otpRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Step 1 username and password
        // Step 2 username and otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if (otp != null) {
            // step 2
            Authentication a = new OtpAuthentication(username, otp);

            a = authenticationManager.authenticate(a);

            // generate a token
            response.setHeader("Authorization", UUID.randomUUID().toString());

        } else {
            // step 1
            Authentication a = new UsernamePasswordAuthentication(username, password);

            a = authenticationManager.authenticate(a);

            // we generate an OTP

            String code = String.valueOf(new Random().nextInt(9999) + 1000);

            Otp otpEntity = new Otp();

            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);


        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
