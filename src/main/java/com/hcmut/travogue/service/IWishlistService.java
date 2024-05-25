package com.hcmut.travogue.service;

import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.TravelActivity.Wishlist;

import java.util.List;
import java.util.UUID;

public interface IWishlistService {
    public void addToWishlist(UUID userId, UUID activityId);

    public void removeFromWishlist(UUID userId, UUID activityId);

    public List<TravelActivity> getWishlistOfAUser(UUID userId);
}
