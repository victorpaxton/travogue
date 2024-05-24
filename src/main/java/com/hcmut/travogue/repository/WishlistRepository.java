package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.TravelActivity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    void deleteByUser_IdAndTravelActivity_Id(UUID userId, UUID travelActivityId);

    List<Wishlist> findAllByUser_IdOrderByUpdatedAtDesc(UUID userId);
}
