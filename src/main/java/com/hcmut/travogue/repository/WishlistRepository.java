package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.TravelActivity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    Optional<Wishlist> findByUser_IdAndTravelActivity_Id(UUID userId, UUID travelActivityId);

    boolean existsByUser_IdAndTravelActivity_Id(UUID userId, UUID activityId);

    List<Wishlist> findAllByUser_IdOrderByUpdatedAtDesc(UUID userId);
}
