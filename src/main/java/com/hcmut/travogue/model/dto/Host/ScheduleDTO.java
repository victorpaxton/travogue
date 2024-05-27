package com.hcmut.travogue.model.dto.Host;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ScheduleDTO {
    private String mainImage;
    private String activityName;
    private UUID activityTimeFrameId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endAt;
    private int guestSize;
    private int maxGuest;
}
