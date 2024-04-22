package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.User.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, UUID> {
    Optional<UserFollow> findByFrom_IdAndTo_Id(UUID from, UUID to);
}
