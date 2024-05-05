package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.Plan.PlanCity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class City extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String destinations;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text", name = "city_tags")
    private String cityTags;

    private String images;

    private String country;

    @Column(name = "travel_point")
    private Double travelPoint;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<TravelActivity> travelActivities;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<PlanCity> planCities;
}
