package com.hcmut.travogue.service;

import com.hcmut.travogue.model.entity.User.Host;

import java.util.UUID;

public interface IHostService {
    Host getHost(UUID userId);
}
