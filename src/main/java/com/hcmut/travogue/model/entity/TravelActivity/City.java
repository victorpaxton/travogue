package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
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

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text", name = "city_tags")
    private String cityTags;

    private String country;

    @Column(name = "travel_point")
    private Double travelPoint;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<TravelActivity> travelActivities;
}
