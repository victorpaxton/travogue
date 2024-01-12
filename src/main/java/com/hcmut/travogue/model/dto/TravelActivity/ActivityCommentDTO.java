package com.hcmut.travogue.model.dto.TravelActivity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ActivityCommentDTO {
    @NotEmpty(message = "Please rating this activity")
    double rating;

    @NotEmpty(message = "Please write a comment")
    String comment;
}
