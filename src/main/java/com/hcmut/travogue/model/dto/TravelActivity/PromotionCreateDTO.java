package com.hcmut.travogue.model.dto.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.PromotionType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class PromotionCreateDTO {
    private String promotionName;

    private String discountCode;

    private String discountRate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;
}
