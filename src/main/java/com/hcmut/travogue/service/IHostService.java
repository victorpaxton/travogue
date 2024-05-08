package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.User.HostDetail;
import com.hcmut.travogue.model.entity.User.Host;

import java.util.UUID;

public interface IHostService {
    HostDetail getHost(UUID userId);
}
