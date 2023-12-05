package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.exception.BadRequestException;
import com.hcmut.travogue.model.TokenType;
import com.hcmut.travogue.model.dto.Auth.AuthenticationResponseDTO;
import com.hcmut.travogue.model.dto.Auth.LoginDTO;
import com.hcmut.travogue.model.dto.Auth.RefreshTokenRequest;
import com.hcmut.travogue.model.dto.Auth.TokenDTO;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.entity.Auth.OTPCode;
import com.hcmut.travogue.model.entity.Auth.Token;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Auth.OTPCodeRepository;
import com.hcmut.travogue.repository.Auth.TokenRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IAuthService;
import com.hcmut.travogue.util.EmailService;
import com.hcmut.travogue.util.JwtService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPCodeRepository otpCodeRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

    @Override
    @Transactional
    public void getOTPCode(String email) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(email))
            throw new BadRequestException("Email already in use");

        if (otpCodeRepository.existsByEmail(email))
            otpCodeRepository.deleteByEmail(email);

        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        String encodedOTP = passwordEncoder.encode(otp);

        otpCodeRepository.save(OTPCode.builder().otpCode(encodedOTP).email(email).build());

        emailService.sendOTPEmail(email, otp);
    }

    @Override
    @Transactional
    public String validateOTP(String email, String otp) {

        Optional<OTPCode> otpCode = otpCodeRepository.findByEmail(email);

        if (otpCode.isEmpty())
            throw new BadRequestException("Invalid or expired OTP code");

        if (!passwordEncoder.matches(otp, otpCode.get().getOtpCode()))
            throw new BadRequestException("Invalid or expired OTP code");

        if (otpCode.get().getCreatedAt().getTime() + OTP_VALID_DURATION < System.currentTimeMillis())
            throw new BadRequestException("Invalid or expired OTP code");

        otpCode.get().setVerified(true);
        otpCodeRepository.save(otpCode.get());

        return jwtService.buildTokenByEmail(email, 86400000, TokenType.VERIFY_EMAIL);
    }

    @Override
    @Transactional
    public AuthenticationResponseDTO register(String token, LoginDTO loginDTO) {
        try {
            jwtService.validateToken(token);
        } catch (Exception e) {
            throw new BadRequestException("Token expired. Verify your email again");
        }

        Optional<OTPCode> otpCode = otpCodeRepository.findByEmail(jwtService.extractId(token));

        if (otpCode.isEmpty() || !otpCode.get().isVerified())
            throw new BadRequestException("Please verify your email");

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());

        User newUser = userRepository.save(
                User.builder()
                .email(jwtService.extractId(token))
                .firstName("")
                .lastName("")
                .password(encodedPassword)
                .phone("")
                .avatar("/user/avatar/default.jpg")
                .bioIntro("Write something about you!")
                .roles("ROLE_USER")
                .isEnabled(true)
                .build()
        );

        String refreshToken = jwtService.generateRefreshTokenByUser(newUser);
        String accessToken = jwtService.generateTokenByUser(newUser);

        tokenService.addToken(refreshToken, TokenType.REFRESH, newUser);

        otpCodeRepository.delete(otpCode.get());

        return AuthenticationResponseDTO.builder()
                .user(modelMapper.map(newUser, UserProfileDTO.class))
                .tokens(AuthenticationResponseDTO.TokenResponse.builder()
                        .access(new TokenDTO(accessToken, jwtService.extractExpiration(accessToken)))
                        .refresh(new TokenDTO(refreshToken, jwtService.extractExpiration(refreshToken)))
                        .build())
                .build();
    }

    @Override
    public AuthenticationResponseDTO login(LoginDTO loginDTO) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        );

        try {
            authentication = authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new AuthenticationException("Wrong username or password") {
            };
        }

        if (authentication.isAuthenticated()) {
            SessionUser userDetails = (SessionUser) authentication.getPrincipal();

            String refreshToken = jwtService.generateRefreshTokenByUser(userDetails.getUserInfo());
            String accessToken = jwtService.generateTokenByUser(userDetails.getUserInfo());

            tokenService.addToken(refreshToken, TokenType.REFRESH, userDetails.getUserInfo());

            return AuthenticationResponseDTO.builder()
                    .user(modelMapper.map(userDetails.getUserInfo(), UserProfileDTO.class))
                    .tokens(AuthenticationResponseDTO.TokenResponse.builder()
                            .access(new TokenDTO(accessToken, jwtService.extractExpiration(accessToken)))
                            .refresh(new TokenDTO(refreshToken, jwtService.extractExpiration(refreshToken)))
                            .build())
                    .build();
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }

    @Override
    public AuthenticationResponseDTO.TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Token refreshToken = tokenRepository.findByStrToken(refreshTokenRequest.getToken())
                .orElseThrow(() -> new AuthenticationException("Refresh token not found") {});

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateTokenByUser(user);

        long remaining = jwtService.extractExpiration(refreshTokenRequest.getToken()).getTime() - new Date().getTime();
        String newRefreshToken = jwtService.regenerateRefreshTokenByUser(user, remaining);

        refreshToken.setStrToken(newRefreshToken);
        refreshToken.setExpires(jwtService.extractExpiration(newRefreshToken));
        tokenRepository.save(refreshToken);

        return AuthenticationResponseDTO.TokenResponse.builder()
                .access(new TokenDTO(newAccessToken, jwtService.extractExpiration(newAccessToken)))
                .refresh(new TokenDTO(newRefreshToken, jwtService.extractExpiration(newRefreshToken)))
                .build();
    }

    @Override
    public void forgotPassword(String email) throws MessagingException, UnsupportedEncodingException {
        if (!userRepository.existsByEmail(email))
            throw new BadRequestException("Not account found with email " + email);

        if (otpCodeRepository.existsByEmail(email))
            otpCodeRepository.deleteByEmail(email);

        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        String encodedOTP = passwordEncoder.encode(otp);

        otpCodeRepository.save(OTPCode.builder().otpCode(encodedOTP).email(email).build());

        emailService.sendOTPEmail(email, otp);
    }

    @Override
    @Transactional
    public void resetPassword(String token, LoginDTO loginDTO) {
        try {
            jwtService.validateToken(token);
        } catch (Exception e) {
            throw new BadRequestException("Token expired. Verify your email again");
        }

        Optional<OTPCode> otpCode = otpCodeRepository.findByEmail(loginDTO.getEmail());

        if (otpCode.isEmpty() || !otpCode.get().isVerified())
            throw new BadRequestException("Your email is not verified yet. Please back to the first step");

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new BadRequestException("Your email is not registered yet"));

        user.setPassword(encodedPassword);
        userRepository.save(user);

        otpCodeRepository.delete(otpCode.get());
    }

}
