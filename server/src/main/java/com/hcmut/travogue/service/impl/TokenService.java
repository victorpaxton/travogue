package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.TokenType;
import com.hcmut.travogue.model.entity.Auth.Token;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Auth.TokenRepository;
import com.hcmut.travogue.service.ITokenService;
import com.hcmut.travogue.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements ITokenService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token addToken(String token, TokenType type, User user) {
        Token newToken = Token.builder()
                .strToken(token)
                .type(type)
                .expires(jwtService.extractExpiration(token))
                .user(user)
                .build();

        return tokenRepository.save(newToken);
    }
}
