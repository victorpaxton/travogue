package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.UserRegisterDTO;
import com.hcmut.travogue.model.entity.User.User;

public interface IUserService {
    User addUser(UserRegisterDTO userRegisterDTO);
}
