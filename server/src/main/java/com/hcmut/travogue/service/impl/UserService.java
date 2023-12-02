package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.UserRegisterDTO;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public User addUser(UserRegisterDTO userRegisterDTO) {
        return userRepository.save(modelMapper.map(userRegisterDTO, User.class));
    }
}
