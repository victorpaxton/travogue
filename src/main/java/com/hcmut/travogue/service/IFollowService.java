package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.User.User;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IFollowService {

    public void followUser(Principal principal, UUID toId);

    public void unfollowUser(Principal principal, UUID toId);

    public List<UserShortProfileDTO> getFollowers(Principal principal);

    public List<UserShortProfileDTO> getFollowing(Principal principal);
}
