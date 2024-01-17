package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.TravelActivity.PromotionCreateDTO;
import com.hcmut.travogue.model.entity.TravelActivity.Promotion;

import java.util.List;
import java.util.UUID;

public interface IPromotionService {

    public Promotion addPromotion(UUID activityId, PromotionCreateDTO promotionCreateDTO);

    public List<Promotion> getPromotionsByActivity(UUID activityId);

    public String checkValidDiscountCode(UUID activityId, String discountCode);

}
