package com.hcmut.travogue.model.dto.Post;

import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.User;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
