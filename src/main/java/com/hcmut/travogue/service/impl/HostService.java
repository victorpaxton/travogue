package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Host.ScheduleDTO;
import com.hcmut.travogue.model.dto.TravelActivity.TravelActivityShortResponse;
import com.hcmut.travogue.model.dto.User.HostDetail;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityDate;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.repository.HostRepository;
import com.hcmut.travogue.repository.TravelActivity.ActivityCommentRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.IHostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class HostService implements IHostService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private ActivityCommentRepository activityCommentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public HostDetail getHost(UUID hostId) {
        HostDetail res = modelMapper.map(hostRepository.findById(hostId).orElseThrow(), HostDetail.class);
        res.setHotTour(
                travelActivityRepository.findFirst10ByHost_IdOrderByAverageRatingDesc(hostId)
                        .stream().map(travelActivity -> {
                            TravelActivityShortResponse travelShort = modelMapper.map(travelActivity, TravelActivityShortResponse.class);
                            travelShort.setCategoryName(travelActivity.getActivityCategory().getCategoryName());
                            travelShort.setCityName(travelActivity.getCity().getName());
                            travelShort.setRating(travelActivity.getAverageRating());
                            travelShort.setNumberOfRating(activityCommentRepository.countAllByTravelActivity_Id(travelActivity.getId()));
                            return travelShort;
                        }).toList()
        );
        res.setNumOfActivities(travelActivityRepository.countAllByHost_Id(hostId));
        res.setNumOfCities(travelActivityRepository.countDistinctCityByHostId(hostId));

        return res;
    }

    @Override
    public List<Date> getActiveDate(UUID hostId) {
        Host host = hostRepository.findById(hostId).orElseThrow();
        return host.getTravelActivities()
                .stream().flatMap(travelActivity ->
                        travelActivity.getActivityDates()
                                .stream().map(ActivityDate::getDate)).distinct().toList();
    }

    @Override
    public List<ScheduleDTO> getScheduleInADay(UUID hostId, Date date) {
        Host host = hostRepository.findById(hostId).orElseThrow();
        List<TravelActivity> travelActivities = host.getTravelActivities();

        return travelActivities.stream().map(travelActivity -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setActivityName(travelActivity.getActivityName());
            scheduleDTO.setMainImage(travelActivity.getMainImage());

            travelActivity.getActivityDates().stream().filter(
                    activityDate -> activityDate.getDate() == date
            ).forEach(activityDate -> {
                activityDate.getActivityTimeFrames().forEach(
                        activityTimeFrame -> {
                            scheduleDTO.setStartAt(activityTimeFrame.getStartAt());
                            scheduleDTO.setEndAt(activityTimeFrame.getEndAt());
                            scheduleDTO.setMaxGuest(activityTimeFrame.getMaximumGuests());
                            scheduleDTO.setGuestSize(activityTimeFrame.getNumOfRegisteredGuests());
                        }
                );
            });
            return scheduleDTO;
        }).toList();
    }
}
