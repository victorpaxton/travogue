package com.hcmut.travogue.model.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "host")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Host extends User {
    @Column(columnDefinition = "text", name = "personal_skills")
    private String personalSkills;

    @Column(columnDefinition = "text", name = "self_introduction")
    private String selfIntroduction;

    @Column(name = "average_rating")
    private Double averageRating;

    @OneToMany(mappedBy = "host")
    @JsonIgnore
    private List<TravelActivity> travelActivities;
}
