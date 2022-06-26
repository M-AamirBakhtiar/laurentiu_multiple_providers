package com.aamirbakhtiar.multiple_providers.repository;

import com.aamirbakhtiar.multiple_providers.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}
