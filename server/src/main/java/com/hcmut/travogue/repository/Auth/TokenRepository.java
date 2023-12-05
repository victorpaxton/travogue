package com.hcmut.travogue.repository.Auth;

import com.hcmut.travogue.model.entity.Auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByStrToken(String token);
}
