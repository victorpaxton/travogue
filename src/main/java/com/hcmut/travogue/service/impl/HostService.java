package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.repository.HostRepository;
import com.hcmut.travogue.service.IHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HostService implements IHostService {

    @Autowired
    private HostRepository hostRepository;

    @Override
    public Host getHost(UUID userId) {
        return hostRepository.findById(userId).orElseThrow();
    }
}
