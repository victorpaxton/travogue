package com.hcmut.travogue.service;

import com.hcmut.travogue.model.entity.User.User;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IFollowService {

    public void followUser(Principal principal, UUID toId);

    public void unfollowUser(Principal principal, UUID toId);

    public List<User> getFollowers(Principal principal);

    public List<User> getFollowing(Principal principal);
}
