package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.file.CloudinaryService;
import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.TravelActivity.*;
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
import java.util.Objects;
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
    public CommentResponseDTO comment(Principal principal, UUID activityId, ActivityCommentDTO activityCommentDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();

        ActivityComment newComment = ActivityComment.builder()
                .rating(activityCommentDTO.getRating())
                .comment(activityCommentDTO.getComment())
                .user(user)
                .travelActivity(activity)
                .build();

        double newRating = travelActivityRepository.calcAvgRating(activityId, activityCommentDTO.getRating());

        activity.setAverageRating(newRating);
        travelActivityRepository.save(activity);

        return CommentResponseDTO.builder()
                .newAvgRating(newRating)
                .activityComment(activityCommentRepository.save(newComment))
                .build();
    }

    @Override
    public List<ActivityComment> getCommentsByActivity(Principal principal, UUID activityId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        List<ActivityComment> comments = activityCommentRepository.findByTravelActivityIdOrderByCreatedAtDesc(activityId);

        List<ActivityComment> matchingComments = comments.stream()
                .filter(comment -> comment.getUser().getId().equals(user.getId()))
                .toList();

        comments.removeAll(matchingComments);
        comments.addAll(0, matchingComments);

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
    public PageResponse<TravelActivity> getActivitiesByHost(UUID hostId, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());

        return new PageResponse<>(
                travelActivityRepository.findByHost_Id(hostId, pageable)
        );
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
    public TravelActivity uploadImage(UUID activityId, MultipartFile file) throws IOException {
        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();
        String contentType = file.getContentType();
        String cur = activity.getImages();
        if (Objects.equals(cur, "")) {
            if (contentType.startsWith("image/"))
                activity.setImages(cloudinaryService.uploadFile("travel_activity", file));
            else if (contentType.startsWith("video/"))
                activity.setImages(cloudinaryService.uploadVideo("travel_activity", file));
        }
        else {
            if (contentType.startsWith("image/"))
                activity.setImages(cur + ";" + cloudinaryService.uploadFile("travel_activity", file));
            else if (contentType.startsWith("video/"))
                activity.setImages(cur + ";" + cloudinaryService.uploadVideo("travel_activity", file));
        }

        return travelActivityRepository.save(activity);
    }

    @Override
    public TravelActivity uploadVideo(UUID activityId, MultipartFile video) throws IOException {
        TravelActivity activity = travelActivityRepository.findById(activityId).orElseThrow();
        String cur = activity.getImages();
        if (Objects.equals(cur, ""))
            activity.setImages(cloudinaryService.uploadVideo("travel_activity", video));
        else
            activity.setImages(cur + ";" + cloudinaryService.uploadVideo("travel_activity", video));
        return travelActivityRepository.save(activity);
    }

    @Override
    public PageResponse<TravelActivityShortResponse> searchActivities(int pageNumber, int pageSize, String sortField, String criteria) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        return new PageResponse<>(
                travelActivityRepository.findPageActivities(criteria, pageable)
                        .map(travelActivity -> {
                            TravelActivityShortResponse res = modelMapper.map(travelActivity, TravelActivityShortResponse.class);
                            res.setCategoryName(travelActivity.getActivityCategory().getCategoryName());
                            res.setCityName(travelActivity.getCity().getName());
                            res.setRating(travelActivity.getAverageRating());
                            res.setNumberOfRating(activityCommentRepository.countAllByTravelActivity_Id(travelActivity.getId()));
                            return res;
                        })
        );
    }
}
