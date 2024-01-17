package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.exception.BadRequestException;
import com.hcmut.travogue.model.dto.TravelActivity.PromotionCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.Promotion;
import com.hcmut.travogue.repository.TravelActivity.PromotionRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.service.IPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PromotionService implements IPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Promotion addPromotion(UUID activityId, PromotionCreateDTO promotionCreateDTO) {
        Promotion newPromotion = modelMapper.map(promotionCreateDTO, Promotion.class);
        newPromotion.setTravelActivity(travelActivityRepository.findById(activityId).orElseThrow());
        return promotionRepository.save(newPromotion);
    }

    @Override
    public List<Promotion> getPromotionsByActivity(UUID activityId) {
        return travelActivityRepository.findById(activityId).orElseThrow().getPromotions();
    }

    @Override
    public String checkValidDiscountCode(UUID activityId, String discountCode) {
        Promotion promotion = promotionRepository.findByDiscountCodeAndTravelActivityId(discountCode, activityId)
                .orElseThrow(() -> new BadRequestException("Invalid discount code"));

        boolean isNotExpire = Instant.now().isAfter(promotion.getStartDate().toInstant()) && Instant.now().isBefore(promotion.getEndDate().toInstant());

        if (!isNotExpire)
            throw new BadRequestException("Expired discount code");

        return promotion.getDiscountRate();
    }
}
