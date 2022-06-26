package com.aamirbakhtiar.multiple_providers.repository;

import com.aamirbakhtiar.multiple_providers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
