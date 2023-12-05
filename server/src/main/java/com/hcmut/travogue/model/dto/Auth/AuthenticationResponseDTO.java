package com.hcmut.travogue.model.dto.Auth;

import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private UserProfileDTO user;
    private TokenResponse tokens;

    @Data
    @Builder
    public static class TokenResponse {
        private TokenDTO access;
        private TokenDTO refresh;
    }
}
