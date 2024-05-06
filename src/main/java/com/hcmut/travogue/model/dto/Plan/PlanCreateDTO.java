package com.hcmut.travogue.model.dto.Plan;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlanCreateDTO {
    private String planName;

    private List<UUID> citiesId;
}
