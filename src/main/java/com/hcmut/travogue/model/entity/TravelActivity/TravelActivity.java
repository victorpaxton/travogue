package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.Plan.PlanActivity;
import com.hcmut.travogue.model.entity.Ticket.InsuranceActivity;
import com.hcmut.travogue.model.entity.Ticket.InsuranceCompany;
import com.hcmut.travogue.model.entity.User.Host;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "travel_activity")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class TravelActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String tags;

    @Column(columnDefinition = "text")
    private String languages;

    @Column(columnDefinition = "text", name = "personal_options")
    private String personalOptions;

    @Column(name = "main_image")
    private String mainImage;

    @Column(columnDefinition = "text")
    private String images;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "general_price")
    private Integer generalPrice;

    @Column(name = "travel_point")
    private Double travelPoint;

    @ManyToOne
    @JoinColumn(name = "activity_category_id", referencedColumnName = "id")
    private ActivityCategory activityCategory;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityDate> activityDates;

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;

    @OneToMany(mappedBy = "travelActivity")
    @JsonIgnore
    private List<ActivityComment> activityComments;

    @OneToMany(mappedBy = "travelActivity")
    @JsonIgnore
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "travelActivity")
    @JsonIgnore
    private List<PlanActivity> planActivities;

    @OneToMany(mappedBy = "travelActivity")
    @JsonIgnore
    private List<InsuranceActivity> insuranceActivities;
}
