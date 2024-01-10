package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;

import java.util.UUID;

public interface IUserService {
    public UserProfileDTO getUser(UUID userId);

    public PageResponse<UserProfileDTO> getUsers(String keyword, int pageNumber, int pageSize, String sortField);
}
