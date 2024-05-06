package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Plan.AddActivityDTO;
import com.hcmut.travogue.model.dto.Plan.PlanCreateDTO;
import com.hcmut.travogue.model.dto.Plan.PlanDetail;
import com.hcmut.travogue.model.entity.Plan.Plan;
import com.hcmut.travogue.model.entity.Plan.PlanActivity;
import com.hcmut.travogue.model.entity.Plan.PlanCity;
import com.hcmut.travogue.model.entity.TravelActivity.City;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Plan.PlanActivityRepository;
import com.hcmut.travogue.repository.Plan.PlanCityRepository;
import com.hcmut.travogue.repository.Plan.PlanRepository;
import com.hcmut.travogue.repository.TravelActivity.CityRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.IPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class PlanService implements IPlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanCityRepository planCityRepository;

    @Autowired
    private PlanActivityRepository planActivityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Plan createPlan(Principal principal, PlanCreateDTO planCreateDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        Plan newPlan = planRepository.save(Plan.builder().planName(planCreateDTO.getPlanName()).user(user).build());
        planCreateDTO.getCitiesId()
                .forEach(cityId -> {
                    City city = cityRepository.findById(cityId).orElseThrow();
                    planCityRepository.save(PlanCity.builder().plan(newPlan).city(city).build());
                });
        return planRepository.findById(newPlan.getId()).orElseThrow();
    }

    @Override
    public Plan addImage(UUID planId, MultipartFile image) {
        return null;
    }

    @Override
    public Plan editPlanInfo(UUID planId, PlanCreateDTO planCreateDTO) {
        Plan plan = planRepository.findById(planId).orElseThrow();
        plan.setPlanName(planCreateDTO.getPlanName());
        return null;
    }

    @Override
    public List<Plan> getPlansByUser(UUID userId) {
        return planRepository.findAllByUser_Id(userId);
    }

    @Override
    public PlanDetail addActivityToPlan(UUID planId, AddActivityDTO addActivityDTO) {
        Plan plan = planRepository.findById(planId).orElseThrow();
        addActivityDTO.getActivitiesId()
                .forEach(activityId -> planActivityRepository.save(
                        PlanActivity.builder()
                                .plan(plan)
                                .travelActivity(travelActivityRepository.findById(activityId).orElseThrow())
                                .day(addActivityDTO.getDay())
                                .build()
                ));
        return modelMapper.map(planRepository.findById(planId), PlanDetail.class);
    }

    @Override
    public void removeActivityFromPlan(UUID planId, UUID activityId) {
        PlanActivity planActivity = planActivityRepository.findByPlan_IdAndTravelActivity_Id(planId, activityId);
        planActivityRepository.delete(planActivity);
    }

    @Override
    public PlanDetail getPlan(UUID planId) {
        return modelMapper.map(planRepository.findById(planId).orElseThrow(), PlanDetail.class);
    }
}
