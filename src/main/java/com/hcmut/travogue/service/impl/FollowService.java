package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.model.entity.User.UserFollow;
import com.hcmut.travogue.repository.UserFollowRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IFollowService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

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
    public List<UserShortProfileDTO> getFollowers(Principal principal, UUID userId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        return userFollowRepository.findAllByTo_Id(userId)
                .stream().map(userFollow -> {
                    UserShortProfileDTO res = modelMapper.map(userFollow.getFrom(), UserShortProfileDTO.class);
                    res.setFollowStatus(userFollowRepository.existsByFrom_IdAndTo_Id(user.getId(), res.getId()));
                    return res;
                    }
                ).toList();
    }

    @Override
    public List<UserShortProfileDTO> getFollowing(Principal principal, UUID userId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        return userFollowRepository.findAllByFrom_Id(userId)
                .stream().map(userFollow -> {
                            UserShortProfileDTO res = modelMapper.map(userFollow.getTo(), UserShortProfileDTO.class);
                            res.setFollowStatus(userFollowRepository.existsByFrom_IdAndTo_Id(user.getId(), res.getId()));
                            return res;
                        }
                ).toList();
    }
}
