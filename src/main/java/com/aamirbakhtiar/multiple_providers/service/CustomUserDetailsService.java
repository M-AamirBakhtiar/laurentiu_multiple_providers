package com.aamirbakhtiar.multiple_providers.service;

import com.aamirbakhtiar.multiple_providers.entity.User;
import com.aamirbakhtiar.multiple_providers.repository.UserRepository;
import com.aamirbakhtiar.multiple_providers.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByUsername(username);

        User u = user.orElseThrow(() -> new UsernameNotFoundException("user was not found with username: " + username));

        return new SecurityUser(u);
    }
}
