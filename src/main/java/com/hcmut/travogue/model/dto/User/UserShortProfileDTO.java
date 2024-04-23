package com.hcmut.travogue.model.dto.User;

import lombok.Data;

import java.util.UUID;

@Data
public class UserShortProfileDTO {
    private UUID id;

    private String email;

    private String firstName;
    private String lastName;
    private String avatar;
}
