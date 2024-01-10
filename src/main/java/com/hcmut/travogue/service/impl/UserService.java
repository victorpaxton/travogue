package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserProfileDTO getUser(UUID userId) {
        return modelMapper.map(userRepository.findById(userId), UserProfileDTO.class);
    }

    public PageResponse<UserProfileDTO> getUsers(String keyword, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        return new PageResponse<>(userRepository.findPageUsers(keyword, pageable)
                .map(user -> modelMapper.map(user, UserProfileDTO.class)));
    }
}
