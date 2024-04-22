package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Post.PostCommentDTO;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IFollowService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private IFollowService followService;

    @PostMapping("/{userId}")
    @Operation(summary = "Follow a user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> followUser(Principal principal, @PathVariable("userId") UUID toId) {
        followService.followUser(principal, toId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Success")
                .errors(null)
                .build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Unfollow a user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> unfollowUser(Principal principal, @PathVariable("userId") UUID toId) {
        followService.unfollowUser(principal, toId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Success")
                .errors(null)
                .build();
    }

    @GetMapping("/followers")
    @Operation(summary = "Get followers list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getFollowers(Principal principal) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(followService.getFollowers(principal))
                .errors(null)
                .build();
    }

    @GetMapping("/following")
    @Operation(summary = "Get following list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getFollowing(Principal principal) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(followService.getFollowing(principal))
                .errors(null)
                .build();
    }
}
