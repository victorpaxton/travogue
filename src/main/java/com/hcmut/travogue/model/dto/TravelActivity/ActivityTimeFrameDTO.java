package com.hcmut.travogue.model.dto.TravelActivity;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityTimeFrameDTO {
    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;

    private int maximumGuests;

    private String languages;

    private String hostNotes;

    private int adultsPrice;

    private int childrenPrice;

    private int babyPrice;
}
