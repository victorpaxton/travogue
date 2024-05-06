package com.hcmut.travogue.model.dto.Plan;

import com.hcmut.travogue.model.entity.Plan.PlanActivity;
import com.hcmut.travogue.model.entity.Plan.PlanCity;
import com.hcmut.travogue.model.entity.User.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlanDetail {
    private UUID id;
    private String planName;
    private User user;
    private List<PlanCity> planCities;
    private List<PlanActivity> planActivities;
}
