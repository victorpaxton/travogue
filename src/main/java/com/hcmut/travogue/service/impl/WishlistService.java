package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.entity.TravelActivity.Wishlist;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.repository.WishlistRepository;
import com.hcmut.travogue.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistService implements IWishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Override
    public void addToWishlist(UUID userId, UUID activityId) {
        wishlistRepository.save(
                Wishlist.builder()
                        .user(userRepository.findById(userId).orElseThrow())
                        .travelActivity(travelActivityRepository.findById(activityId).orElseThrow())
                        .build()
        );
    }

    @Override
    public void removeFromWishlist(UUID userId, UUID activityId) {
        wishlistRepository.deleteByUser_IdAndTravelActivity_Id(userId, activityId);
    }

    @Override
    public List<Wishlist> getWishlistOfAUser(UUID userId) {
        return wishlistRepository.findAllByUser_IdOrderByUpdatedAtDesc(userId);
    }
}
