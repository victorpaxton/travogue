package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.model.entity.User.UserFollow;
import com.hcmut.travogue.repository.UserFollowRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class FollowService implements IFollowService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;
    @Override
    public void followUser(Principal principal, UUID toId) {
        User from = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        User to = userRepository.findById(toId).orElseThrow();

        UserFollow userFollow = UserFollow.builder()
                .from(from)
                .to(to)
                .build();

        userFollowRepository.save(userFollow);
    }

    @Override
    public void unfollowUser(Principal principal, UUID toId) {
        User from = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        User to = userRepository.findById(toId).orElseThrow();

        UserFollow userFollow = userFollowRepository.findByFrom_IdAndTo_Id(from.getId(), to.getId())
                .orElseThrow();

        userFollowRepository.delete(userFollow);
    }

    @Override
    public List<User> getFollowers(Principal principal) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        return user.getFollowers().stream().map(UserFollow::getFrom).toList();
    }

    @Override
    public List<User> getFollowing(Principal principal) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        return user.getFollowing().stream().map(UserFollow::getTo).toList();
    }
}
