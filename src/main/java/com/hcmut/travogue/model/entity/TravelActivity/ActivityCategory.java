package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "activity_category")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ActivityCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(columnDefinition = "text")
    private String description;

    private String image;

    @Column(columnDefinition = "text")
    private String svg;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonBackReference
    @JsonIgnore
    private ActivityCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<ActivityCategory> childCategories;

    @OneToMany(mappedBy = "activityCategory")
    @JsonIgnore
    private List<TravelActivity> activities;
}
