package com.hcmut.travogue.service;

import com.hcmut.travogue.model.TokenType;
import com.hcmut.travogue.model.entity.Auth.Token;
import com.hcmut.travogue.model.entity.User.User;

public interface ITokenService {
    Token addToken(String token, TokenType type, User user);
}
