package com.hcmut.travogue.model.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OTPVerificationDTO {
    @Email(message = "Email is not valid")
    @Schema(example = "hhqthang@gmail.com")
    private String email;

    @Pattern(regexp = "[0-9]{6}")
    private String otp;
}
