package com.hcmut.travogue.model.dto.TravelActivity;

import lombok.Data;

@Data
public class ActivityCreateDTO {
    private String activityName;
    private String description;
    private String tags;
    private String languages;
}
