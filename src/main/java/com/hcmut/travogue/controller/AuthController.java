package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Auth.*;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/otp/generate")
    @Operation(summary = "Get OTP code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getOTPCode(@RequestBody @Valid EmailDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        authService.getOTPCode(emailDTO.getEmail());

        return ResponseModel.builder()
                .isSuccess(true)
                .data("OTP Code has been sent to your email")
                .errors(null)
                .build();
    }

    @PostMapping("/otp/verification")
    @Operation(summary = "Verify the OTP Code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> validateOTP(@RequestBody @Valid OTPVerificationDTO otpVerificationDTO) {
        String token = authService.validateOTP(otpVerificationDTO.getEmail(), otpVerificationDTO.getOtp());

        return ResponseModel.builder()
                .isSuccess(true)
                .data(token)
                .errors(null)
                .build();
    }

    @PostMapping("/register")
    @Operation(summary = "Register account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> register(@RequestParam("token") String token, @RequestBody @Valid LoginDTO loginDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(authService.register(token, loginDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> login(@RequestBody @Valid LoginDTO loginDTO) throws AuthenticationException {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(authService.login(loginDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/refresh-tokens")
    @Operation(summary = "Refresh tokens")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(authService.refreshToken(refreshTokenRequest))
                .errors(null)
                .build();
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> forgotPassword(@RequestBody @Valid EmailDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        authService.forgotPassword(emailDTO.getEmail());

        return ResponseModel.builder()
                .isSuccess(true)
                .data("OTP Code has been sent to your email")
                .errors(null)
                .build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> resetPassword(@RequestParam("token") String token, @RequestBody @Valid LoginDTO loginDTO) {
        authService.resetPassword(token, loginDTO);

        return ResponseModel.builder()
                .isSuccess(true)
                .data("Reset password successfully")
                .errors(null)
                .build();
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest);

        return ResponseModel.builder()
                .isSuccess(true)
                .data("Log out successfully")
                .errors(null)
                .build();
    }

    @PostMapping("/register-v2")
    @Operation(summary = "Register account with username, email and password")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> registerv2(@RequestBody @Valid RegisterV2 registerV2) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(authService.registerV2(registerV2))
                .errors(null)
                .build();
    }
}
