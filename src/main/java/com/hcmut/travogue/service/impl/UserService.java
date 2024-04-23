package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.Ticket.TicketResponseDTO;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.repository.Post.PostRepository;
import com.hcmut.travogue.repository.UserFollowRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserProfileDTO getUser(UUID userId) {
        UserProfileDTO res = modelMapper.map(userRepository.findById(userId), UserProfileDTO.class);
        res.setFollowers(userFollowRepository.countAllByTo_Id(userId));
        res.setFollowing(userFollowRepository.countAllByFrom_Id(userId));
        res.setNumOfPosts(postRepository.countAllByUser_Id(userId));
        return res;
    }

    public PageResponse<UserProfileDTO> getUsers(String keyword, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        return new PageResponse<>(userRepository.findPageUsers(keyword, pageable)
                .map(user -> modelMapper.map(user, UserProfileDTO.class)));
    }

    @Override
    public List<TicketResponseDTO> getTicketsByUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow().getTickets()
                .stream().map(ticket -> {
                    TravelActivity activity = ticket.getActivityTimeFrame().getActivityDate().getActivity();
                    return TicketResponseDTO.builder()
                            .activity(activity)
                            .ticket(ticket)
                            .build();
                }).toList();
    }

}
