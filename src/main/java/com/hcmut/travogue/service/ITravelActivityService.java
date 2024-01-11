package com.hcmut.travogue.service;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ITravelActivityService {
    // Retrieve a list of popular travel activities
    public List<TravelActivity> getPopularTravelActivities();

    // Retrieve a specific travel activity by its ID
    public TravelActivity getTravelActivity(UUID activityId);

    // Add a comment and rating to an activity
    public TravelActivity comment(UUID activityId, Double rating, String comment);

    // Create a new travel activity
    public TravelActivity createActivity(UUID hostId, TravelActivity travelActivity);

    // Retrieve activities hosted by a specific user
    public List<TravelActivity> getActivitiesByHost(UUID hostId);

    // Delete a travel activity by its ID
    public void deleteActivity(UUID activityId);

    // Update an existing travel activity
    public TravelActivity updateActivity(UUID activityId);

    // Retrieve the schedule for a host on a given date
    public List<ActivityTimeFrame> getScheduleForHost(UUID hostId, Date date);

}
