package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "activity_date")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ActivityDate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(columnDefinition = "text", name = "host_notes")
    private String hostNotes;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    @JsonIgnore
    private TravelActivity activity;

    @OneToMany(mappedBy = "activityDate", cascade = CascadeType.ALL)
    private List<ActivityTimeFrame> activityTimeFrames;
}
