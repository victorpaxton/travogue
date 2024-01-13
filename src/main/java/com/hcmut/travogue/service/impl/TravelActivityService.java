package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.file.CloudinaryService;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCommentDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityCreateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityDateDTO;
import com.hcmut.travogue.model.dto.TravelActivity.ActivityTimeFrameDTO;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityDate;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.HostRepository;
import com.hcmut.travogue.repository.TravelActivity.*;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.ITravelActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private CityRepository cityRepository;

    @Autowired
    private ActivityCommentRepository activityCommentRepository;

    @Autowired
    private ActivityDateRepository activityDateRepository;

    @Autowired
    private ActivityTimeFrameRepository activityTimeFrameRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

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

        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();

        ActivityComment newComment = ActivityComment.builder()
                .rating(activityCommentDTO.getRating())
                .comment(activityCommentDTO.getComment())
                .user(user)
                .travelActivity(activity)
                .build();

        activity.setAverageRating(travelActivityRepository.calcAvgRating(activityId, activityCommentDTO.getRating()));
        travelActivityRepository.save(activity);

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
    public TravelActivity createActivity(UUID categoryId, UUID cityId, ActivityCreateDTO activityCreateDTO) {
        TravelActivity newActivity = modelMapper.map(activityCreateDTO, TravelActivity.class);

        newActivity.setActivityCategory(activityCategoryRepository.findById(categoryId).orElseThrow());
        newActivity.setCity(cityRepository.findById(cityId).orElseThrow());
        newActivity.setImages("");
        newActivity.setMainImage("");
        newActivity.setAverageRating((double) 0);

        return travelActivityRepository.save(newActivity);
    }

    @Override
    public TravelActivity createExperience(Principal principal, UUID categoryId, UUID cityId,  ActivityCreateDTO activityCreateDTO) {
        Host host = (Host) ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        TravelActivity newActivity = modelMapper.map(activityCreateDTO, TravelActivity.class);
        newActivity.setActivityCategory(activityCategoryRepository.findById(categoryId).orElseThrow());
        newActivity.setHost(host);
        newActivity.setCity(cityRepository.findById(cityId).orElseThrow());
        newActivity.setImages("");
        newActivity.setMainImage("");
        newActivity.setAverageRating((double) 0);
        return travelActivityRepository.save(newActivity);
    }

    @Override
    public Page<TravelActivity> getActivitiesByHost(UUID hostId, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());

        return travelActivityRepository.findByHost_Id(hostId, pageable);
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

    @Override
    public ActivityDate addActivityDate(UUID activityId, ActivityDateDTO activityDateDTO) {
        ActivityDate newActivityDate = modelMapper.map(activityDateDTO, ActivityDate.class);
        newActivityDate.setActivity(travelActivityRepository.findById(activityId).orElseThrow());
        return activityDateRepository.save(newActivityDate);
    }

    @Override
    public ActivityTimeFrame addActivityTimeFrame(UUID activityDateId, ActivityTimeFrameDTO activityTimeFrameDTO) {
        ActivityTimeFrame activityTimeFrame = modelMapper.map(activityTimeFrameDTO, ActivityTimeFrame.class);
        activityTimeFrame.setNumOfRegisteredGuests(0);
        activityTimeFrame.setActivityDate(activityDateRepository.findById(activityDateId).orElseThrow());

        return activityTimeFrameRepository.save(activityTimeFrame);
    }

    @Override
    public TravelActivity uploadMainImage(UUID activityId, MultipartFile image) throws IOException {
        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();

        activity.setMainImage(cloudinaryService.uploadFile("travel_activity", image));
        return travelActivityRepository.save(activity);
    }

    @Override
    public TravelActivity uploadImage(UUID activityId, MultipartFile image) throws IOException {
        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();
        String cur = activity.getImages();
        activity.setMainImage(cur + ";" + cloudinaryService.uploadFile("travel_activity", image));
        return travelActivityRepository.save(activity);
    }
}
