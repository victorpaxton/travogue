package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Plan.AddActivityDTO;
import com.hcmut.travogue.model.dto.Plan.PlanCreateDTO;
import com.hcmut.travogue.model.dto.Plan.PlanDetail;
import com.hcmut.travogue.model.entity.Plan.Plan;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IPlanService {
    public Plan createPlan(Principal principal, PlanCreateDTO planCreateDTO);
    public Plan addImage(UUID planId, MultipartFile image);
    public Plan editPlanInfo(UUID planId, PlanCreateDTO planCreateDTO);
    public List<Plan> getPlansByUser(UUID userId);
    public PlanDetail addActivityToPlan(UUID planId, AddActivityDTO addActivityDTO);
    public void removeActivityFromPlan(UUID planId, UUID activityId);
    public PlanDetail getPlan(UUID planId);
}
