package com.hcmut.travogue.model.entity.TravelActivity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.Ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "activity_timeframe")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ActivityTimeFrame extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(name = "maximum_guests")
    private int maximumGuests;

    @Column(columnDefinition = "text")
    private String languages;

    @Column(columnDefinition = "text", name = "host_notes")
    private String hostNotes;

    @Column(name = "adults_price")
    private int adultsPrice;

    @Column(name = "children_price")
    private int childrenPrice;

    @Column(name = "baby_price")
    private int babyPrice;

    @Column(name = "num_of_registered_guests")
    private int numOfRegisteredGuests;

    @ManyToOne
    @JoinColumn(name = "activity_date_id", referencedColumnName = "id")
    @JsonIgnore
    private ActivityDate activityDate;

    @OneToMany(mappedBy = "activityTimeFrame")
    @JsonIgnore
    private List<Ticket> tickets;
}
