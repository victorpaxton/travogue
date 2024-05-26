package com.hcmut.travogue.model.dto.Host;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ScheduleDTO {
    private String mainImage;
    private String activityName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;
    private int guestSize;
    private int maxGuest;
}
