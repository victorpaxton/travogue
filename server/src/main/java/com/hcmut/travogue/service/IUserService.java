package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Auth.EmailDTO;
import com.hcmut.travogue.model.entity.User.User;

public interface IUserService {
    User addUser(String email);
}
