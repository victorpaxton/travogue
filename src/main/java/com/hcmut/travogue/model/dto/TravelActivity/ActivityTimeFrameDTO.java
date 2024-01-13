package com.hcmut.travogue.model.dto.TravelActivity;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityTimeFrameDTO {
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    private int maximumGuests;

    private String languages;

    private String hostNotes;

    private int adultsPrice;

    private int childrenPrice;

    private int babyPrice;
}
