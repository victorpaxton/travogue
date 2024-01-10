package com.hcmut.travogue.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "host")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Host extends User {
    @Column(columnDefinition = "text", name = "personal_skills")
    private String personalSkills;

    @Column(columnDefinition = "text", name = "self_introduction")
    private String selfIntroduction;

    @Column(name = "average_rating")
    private Double averageRating;
}
