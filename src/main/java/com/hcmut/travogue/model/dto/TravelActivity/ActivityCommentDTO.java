package com.hcmut.travogue.model.dto.TravelActivity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ActivityCommentDTO {
    double rating;

    @NotEmpty(message = "Please write a comment")
    String comment;
}
