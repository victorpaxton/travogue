package com.hcmut.travogue.model.dto.Post;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostCreateDTO {
    private String caption;
    private List<UUID> usersTagged;
}
