package com.hcmut.travogue.model.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterV2 {
    private String name;

    @Schema(example = "hhqthang@gmail.com")
    @Email(message = "Email is not valid")
    private String email;

    @Schema(example = "Abc@1234")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "The password must contain at least one lowercase character, one uppercase character, one digit, one special character, and a length between 8 to 20.")
    private String password;
}
