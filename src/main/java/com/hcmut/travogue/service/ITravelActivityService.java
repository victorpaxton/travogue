package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.TravelActivity.ActivityCommentDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCreateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityDateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityTimeFrameDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityDate;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ITravelActivityService {
    // Retrieve a list of popular travel activities
    public List<TravelActivity> getPopularTravelActivities();

    // Retrieve a specific travel activity by its ID
    public TravelActivity getTravelActivity(UUID activityId);

    // Add a comment and rating to an activity
    public ActivityComment comment(Principal principal, UUID activityId, ActivityCommentDTO activityCommentDTO);

    public List<ActivityComment> getCommentsByActivity(Principal principal, UUID activityId);

    // Create a new travel activity
    public TravelActivity createActivity(UUID categoryId, ActivityCreateDTO activityCreateDTO);

    // Create a new travel activity (experience)
    public TravelActivity createExperience(Principal principal, ActivityCreateDTO activityCreateDTO);

    // Retrieve activities hosted by a specific user
    public Page<TravelActivity> getActivitiesByHost(UUID hostId, int pageNumber, int pageSize, String sortField);

    // Delete a travel activity by its ID
    public void deleteActivity(UUID activityId);

    // Update an existing travel activity
    public TravelActivity updateActivity(UUID activityId, ActivityCreateDTO activityCreateDTO);

    // Retrieve the schedule for a host on a given date
    public List<ActivityTimeFrame> getScheduleForHost(UUID hostId, Date date);

    ActivityDate addActivityDate(UUID activityId, ActivityDateDTO activityDateDTO);

    ActivityTimeFrame addActivityTimeFrame(UUID activityDateId, ActivityTimeFrameDTO activityTimeFrameDTO);

    TravelActivity uploadMainImage(UUID activityId, MultipartFile image) throws IOException;

    TravelActivity uploadImage(UUID activityId, MultipartFile image) throws IOException;
}
