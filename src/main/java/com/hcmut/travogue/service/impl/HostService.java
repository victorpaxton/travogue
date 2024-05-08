package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.TravelActivity.TravelActivityShortResponse;
import com.hcmut.travogue.model.dto.User.HostDetail;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.repository.HostRepository;
import com.hcmut.travogue.repository.TravelActivity.ActivityCommentRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.IHostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return res;
    }
}
