package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.Ticket.TicketResponseDTO;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.User.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IUserService {
    public UserProfileDTO getUser(Principal principal, UUID userId);

    public PageResponse<UserShortProfileDTO> getUsers(String keyword, int pageNumber, int pageSize, String sortField);

    public List<TicketResponseDTO> getTicketsByUser(UUID userId);

    public User uploadImage(UUID userId, MultipartFile image) throws IOException;

}
