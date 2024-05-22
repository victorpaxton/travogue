package com.hcmut.travogue.model.dto.TravelActivity;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ExperienceCreateDTO {
    private String activityName;
    private String description;
    private String tags;
    private List<ActivityTimeFrameDTO> activityTimeFramesCreate;
    private List<UUID> insurances;
}
