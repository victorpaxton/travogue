package com.hcmut.travogue.model.entity.Ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(name = "insurance_activity")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class InsuranceActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private InsuranceCompany insurance;

    @ManyToOne
    @JoinColumn(name = "travel_activity_id", referencedColumnName = "id")
    @JsonIgnore
    private TravelActivity travelActivity;
}
