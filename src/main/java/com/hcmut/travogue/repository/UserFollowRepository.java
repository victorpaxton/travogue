package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.User.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, UUID> {
    Optional<UserFollow> findByFrom_IdAndTo_Id(UUID from, UUID to);
    List<UserFollow> findAllByFrom_Id(UUID from);

    List<UserFollow> findAllByTo_Id(UUID to);

    int countAllByFrom_Id(UUID from);

    int countAllByTo_Id(UUID to);
}
