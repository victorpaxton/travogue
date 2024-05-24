package com.hcmut.travogue.service;

import java.util.UUID;

public interface IWishlistService {
    public void addToWishlist(UUID userId, UUID activityId);

    public void removeFromWishlist(UUID userId, UUID activityId);
}
