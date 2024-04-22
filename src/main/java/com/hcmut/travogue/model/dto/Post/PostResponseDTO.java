package com.hcmut.travogue.model.dto.Post;

import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.User;
import lombok.Data;

import java.util.UUID;

@Data
public class PostResponseDTO {
    private UUID id;
    private String caption;
    private String images;
    private User user;
    private int numOfComments;
    private int numOfLikes;
    private TravelActivity travelActivity;
}
