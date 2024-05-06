package com.hcmut.travogue.model.dto.Plan;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddActivityDTO {
    private List<UUID> activitiesId;
    private int day;
}
