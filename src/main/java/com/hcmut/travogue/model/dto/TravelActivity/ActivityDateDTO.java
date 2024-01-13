package com.hcmut.travogue.model.dto.TravelActivity;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityDateDTO {
    @Temporal(TemporalType.DATE)
    private Date date;
    private String hostNotes;
}
