package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.ITravelActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TravelActivityService implements ITravelActivityService {

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Override
    public List<TravelActivity> getPopularTravelActivities() {
        return travelActivityRepository.findFirst10ByOrderByTravelPointDesc();
    }

    @Override
    public TravelActivity getTravelActivity(UUID activityId) {
        return travelActivityRepository.findById(activityId).orElseThrow();
    }

    @Override
    public TravelActivity comment(UUID activityId, Double rating, String comment) {
        return null;
    }

    @Override
    public TravelActivity createActivity(UUID hostId, TravelActivity travelActivity) {
        return null;
    }

    @Override
    public List<TravelActivity> getActivitiesByHost(UUID hostId) {
        return null;
    }

    @Override
    public void deleteActivity(UUID activityId) {
        travelActivityRepository.deleteById(activityId);
    }

    @Override
    public TravelActivity updateActivity(UUID activityId) {
        return null;
    }

    @Override
    public List<ActivityTimeFrame> getScheduleForHost(UUID hostId, Date date) {
        return null;
    }
}
