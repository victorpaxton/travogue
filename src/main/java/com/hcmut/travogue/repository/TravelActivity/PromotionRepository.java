package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, UUID> {
    Optional<Promotion> findByDiscountCodeAndTravelActivityId(String discountCode, UUID travelActivityId);
}
