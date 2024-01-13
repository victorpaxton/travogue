package com.hcmut.travogue.model.entity.TravelActivity;

import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "promotion")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_rate")
    private String discountRate;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;

    @ManyToOne
    @JoinColumn(name = "travel_activity_id", referencedColumnName = "id")
    private TravelActivity travelActivity;
}
