package com.hcmut.travogue.model.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailDTO {
    @Schema(example = "hhqthang@gmail.com")
    @Email(message = "Email is not valid")
    private String email;
}
