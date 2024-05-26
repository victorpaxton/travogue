package com.hcmut.travogue.model.dto.Host;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ScheduleRequest {
    @Temporal(TemporalType.DATE)
    private Date date;
}
