package com.hcmut.travogue.model.entity.Ticket;

import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Data
@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Builder
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String notes;

    @Column(name = "adults_price")
    private int adultsPrice;

    @Column(name = "children_price")
    private int childrenPrice;

    @Column(name = "baby_price")
    private int babyPrice;

    @Column(name = "num_of_adults")
    private int numOfAdults;

    @Column(name = "num_of_children")
    private int numOfChildren;

    @Column(name = "num_of_babies")
    private int numOfBabies;

    @Column(name = "total_discount_code")
    private int totalDiscountCode;

    @Column(name = "total_discount_event")
    private int totalDiscountEvent;

    @Column(name = "total_pay")
    private int totalPay;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "payment_info_id", referencedColumnName = "id")
    private PaymentInfo paymentInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_time_frame_id", referencedColumnName = "id")
    private ActivityTimeFrame activityTimeFrame;

    @ManyToOne
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private InsuranceCompany insurance;
}
