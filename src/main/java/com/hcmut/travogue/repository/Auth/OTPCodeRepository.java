package com.hcmut.travogue.repository.Auth;

import com.hcmut.travogue.model.entity.Auth.OTPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OTPCodeRepository extends JpaRepository<OTPCode, UUID> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<OTPCode> findByEmail(String email);
}
