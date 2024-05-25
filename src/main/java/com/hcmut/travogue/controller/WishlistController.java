package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IWishlistService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private IWishlistService wishlistService;

    @PostMapping
    @Operation(summary = "Add to wishlist")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> addToWishlist(@RequestParam("userId") UUID userId, @RequestParam("activityId") UUID activityId) {
        wishlistService.addToWishlist(userId, activityId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Success")
                .errors(null)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Remove from wishlist")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> removeFromWishlist(@RequestParam("userId") UUID userId, @RequestParam("activityId") UUID activityId) {
        wishlistService.removeFromWishlist(userId, activityId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Success")
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get wishlist of a user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getWishlistOfAUser(@RequestParam("userId") UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(wishlistService.getWishlistOfAUser(userId))
                .errors(null)
                .build();
    }
}
