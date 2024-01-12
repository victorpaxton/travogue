package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.User.Host;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HostRepository extends JpaRepository<Host, UUID> {
}
