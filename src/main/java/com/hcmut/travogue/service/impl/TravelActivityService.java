package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.TravelActivity.ActivityCommentDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.HostRepository;
import com.hcmut.travogue.repository.TravelActivity.ActivityCategoryRepository;
import com.hcmut.travogue.repository.TravelActivity.ActivityCommentRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.ITravelActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TravelActivityService implements ITravelActivityService {

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private ActivityCategoryRepository activityCategoryRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityCommentRepository activityCommentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TravelActivity> getPopularTravelActivities() {
        return travelActivityRepository.findFirst10ByOrderByTravelPointDesc();
    }

    @Override
    public TravelActivity getTravelActivity(UUID activityId) {
        return travelActivityRepository.findById(activityId).orElseThrow();
    }

    @Override
    public ActivityComment comment(Principal principal, UUID activityId, ActivityCommentDTO activityCommentDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        ActivityComment newComment = ActivityComment.builder()
                .rating(activityCommentDTO.getRating())
                .comment(activityCommentDTO.getComment())
                .user(user)
                .travelActivity(travelActivityRepository.findById(activityId).orElseThrow())
                .build();

        return activityCommentRepository.save(newComment);
    }

    @Override
    public List<ActivityComment> getCommentsByActivity(Principal principal, UUID activityId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        List<ActivityComment> comments = activityCommentRepository.findByTravelActivityIdOrderByCreatedAtDesc(activityId);

        comments.forEach(activityComment -> {
            if (activityComment.getUser().getId().equals(user.getId())) {
                comments.remove(activityComment);
                comments.add(0, activityComment);
            }
        });

        return comments;
    }

    @Override
    public TravelActivity createActivity(UUID categoryId, ActivityCreateDTO activityCreateDTO) {
        TravelActivity newActivity = modelMapper.map(activityCreateDTO, TravelActivity.class);

        newActivity.setActivityCategory(activityCategoryRepository.findById(categoryId).orElseThrow());

        return travelActivityRepository.save(newActivity);
    }

    @Override
    public TravelActivity createExperience(Principal principal, ActivityCreateDTO activityCreateDTO) {
        Host host = (Host) ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        TravelActivity newActivity = modelMapper.map(activityCreateDTO, TravelActivity.class);
        newActivity.setActivityCategory(activityCategoryRepository.findById(UUID.fromString("df8762e6-e127-4e75-ac55-da372c2fcb09")).orElseThrow());
        newActivity.setHost(host);
        return travelActivityRepository.save(newActivity);
    }

    @Override
    public List<TravelActivity> getActivitiesByHost(UUID hostId, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());

        return hostRepository.findById(hostId, pageable).orElseThrow().getTravelActivities();
    }

    @Override
    public void deleteActivity(UUID activityId) {
        travelActivityRepository.deleteById(activityId);
    }

    @Override
    public TravelActivity updateActivity(UUID activityId, ActivityCreateDTO activityCreateDTO) {
        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();
        activity.setActivityName(activityCreateDTO.getActivityName());
        activity.setDescription(activityCreateDTO.getDescription());
        activity.setTags(activityCreateDTO.getTags());
        activity.setLanguages(activityCreateDTO.getLanguages());
        activity.setPersonalOptions(activityCreateDTO.getPersonalOptions());
        return travelActivityRepository.save(activity);
    }

    @Override
    public List<ActivityTimeFrame> getScheduleForHost(UUID hostId, Date date) {
        return null;
    }
}
