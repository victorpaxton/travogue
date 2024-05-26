package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Host.ScheduleDTO;
import com.hcmut.travogue.model.dto.User.HostDetail;
import com.hcmut.travogue.model.entity.User.Host;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IHostService {
    HostDetail getHost(UUID userId);

    List<Date> getActiveDate(UUID hostId);

    List<ScheduleDTO> getScheduleInADay(UUID hostId, Date date);
}
