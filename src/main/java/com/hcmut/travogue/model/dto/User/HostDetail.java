package com.hcmut.travogue.model.dto.User;

import com.hcmut.travogue.model.dto.TravelActivity.TravelActivityShortResponse;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class HostDetail {
    private UUID id;

    private String email;

    private String firstName;
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String phone;
    private String avatar;
    private String bioIntro;

    private String personalSkills;
    private String languages;
    private String selfIntroduction;

    private List<TravelActivityShortResponse> hotTour;
}
