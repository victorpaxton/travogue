package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Auth.AuthenticationResponseDTO;
import com.hcmut.travogue.model.dto.Auth.LoginDTO;
import com.hcmut.travogue.model.dto.Auth.RefreshTokenRequest;
import com.hcmut.travogue.model.dto.Auth.RegisterV2;
import jakarta.mail.MessagingException;
import org.springframework.security.core.AuthenticationException;

import java.io.UnsupportedEncodingException;

public interface IAuthService {
    public void getOTPCode(String email) throws MessagingException, UnsupportedEncodingException;

    public String validateOTP(String email, String otp);

    public AuthenticationResponseDTO register(String token, LoginDTO loginDTO);

    public AuthenticationResponseDTO login(LoginDTO loginDTO) throws AuthenticationException;

    public AuthenticationResponseDTO.TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    public void forgotPassword(String email) throws MessagingException, UnsupportedEncodingException;

    public void resetPassword(String token, LoginDTO loginDTO);

    public void logout(RefreshTokenRequest refreshTokenRequest);

    public AuthenticationResponseDTO registerV2(RegisterV2 registerV2);
}
