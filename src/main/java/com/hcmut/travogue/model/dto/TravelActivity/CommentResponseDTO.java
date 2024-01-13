package com.hcmut.travogue.model.dto.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDTO {
    private double newAvgRating;
    private ActivityComment activityComment;
}
