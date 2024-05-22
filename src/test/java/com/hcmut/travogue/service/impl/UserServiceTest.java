package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.Ticket.TicketResponseDTO;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.Ticket.Ticket;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityDate;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Post.PostRepository;
import com.hcmut.travogue.repository.UserFollowRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IUserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService underTest;

    @Mock
    UserRepository userRepository;

    @Mock
    UserFollowRepository userFollowRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    Authentication mockAuthentication;

    @Mock
    ModelMapper modelMapper;

    @Test
    void shouldGetLoggingUserProfile() {
        //given
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .email("user@gmail.com")
                .roles("ROLE_USER")
                .build();
        SessionUser sessionUser = new SessionUser(user);
        when(mockAuthentication.getPrincipal()).thenReturn(sessionUser);
        Principal principal = mockAuthentication;
        UserProfileDTO userProfileDTO = UserProfileDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
        when(modelMapper.map(userRepository.findById(userId), UserProfileDTO.class)).thenReturn(userProfileDTO);
        when(userFollowRepository.countAllByTo_Id(userId)).thenReturn(10);
        when(userFollowRepository.countAllByFrom_Id(userId)).thenReturn(5);
        when(postRepository.countAllByUser_Id(userId)).thenReturn(20);
        //when
        UserProfileDTO actualUserProfile = underTest.getUser(principal, userId);
        //then
        assertThat(actualUserProfile).isNotNull();
        assertThat(actualUserProfile.getId()).isEqualTo(user.getId());
        assertThat(actualUserProfile.getEmail()).isEqualTo(user.getEmail());
        assertThat(actualUserProfile.isHost()).isFalse();
        assertThat(actualUserProfile.isFollowStatus()).isFalse();
        assertThat(actualUserProfile.getNumOfFollowers()).isEqualTo(10);
        assertThat(actualUserProfile.getNumOfFollowing()).isEqualTo(5);
        assertThat(actualUserProfile.getNumOfPosts()).isEqualTo(20);
    }

    @Test
    void shouldGetAnotherUserProfile() {
        //given
        User loggingUser = User.builder()
                .id(UUID.randomUUID())
                .email("loggingUser@gmail.com")
                .roles("ROLE_HOST")
                .build();

        User getUser = User.builder()
                .id(UUID.randomUUID())
                .email("getUser@gmail.com")
                .roles("ROLE_USER")
                .build();

        SessionUser sessionUser = new SessionUser(loggingUser);
        when(mockAuthentication.getPrincipal()).thenReturn(sessionUser);
        Principal principal = mockAuthentication;

        UserProfileDTO userProfileDTO = UserProfileDTO
                .builder()
                .id(getUser.getId())
                .email(getUser.getEmail())
                .build();
        when(modelMapper.map(userRepository.findById(getUser.getId()), UserProfileDTO.class)).thenReturn(userProfileDTO);
        when(userFollowRepository.countAllByTo_Id(getUser.getId())).thenReturn(10);
        when(userFollowRepository.countAllByFrom_Id(getUser.getId())).thenReturn(5);
        when(postRepository.countAllByUser_Id(getUser.getId())).thenReturn(20);
        when(userFollowRepository.existsByFrom_IdAndTo_Id(loggingUser.getId(), getUser.getId())).thenReturn(true);

        //when
        UserProfileDTO actualUserProfile = underTest.getUser(principal, getUser.getId());

        //then
        assertThat(actualUserProfile).isNotNull();
        assertThat(actualUserProfile.getId()).isEqualTo(getUser.getId());
        assertThat(actualUserProfile.getEmail()).isEqualTo(getUser.getEmail());
        assertThat(actualUserProfile.isHost()).isTrue();
        assertThat(actualUserProfile.isFollowStatus()).isTrue();
        assertThat(actualUserProfile.getNumOfFollowers()).isEqualTo(10);
        assertThat(actualUserProfile.getNumOfFollowing()).isEqualTo(5);
        assertThat(actualUserProfile.getNumOfPosts()).isEqualTo(20);
    }

    @Test
    void shouldGetUsersPageWithEmptyKeyword() {
        // Given
        String keyword = "";
        int pageNumber = 0;
        int pageSize = 10;
        String sortField = "id";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());

        User user1 = User.builder()
                .id(UUID.randomUUID())
                .email("user1@gmail.com")
                .roles("ROLE_USER")
                .build();

        UserShortProfileDTO user1Profile = UserShortProfileDTO.builder()
                .id(user1.getId())
                .email(user1.getEmail())
                .build();

        List<User> mockUsers = Arrays.asList(new User(), new User()); // Mock user list
        Page<User> mockUsersPage = new PageImpl<>(mockUsers, pageable, mockUsers.size());
        when(userRepository.findPageUsers(keyword, pageable))
                .thenReturn(mockUsersPage);

        when(modelMapper.map(any(User.class), eq(UserShortProfileDTO.class)))
                .thenReturn(user1Profile);

        // When
        PageResponse<UserShortProfileDTO> actualUserPage = underTest.getUsers(keyword, pageNumber, pageSize, sortField);

        // Then (Assertions)
        assertThat(actualUserPage).isNotNull();
        assertThat(actualUserPage.getTotalItems()).isEqualTo(2); // Assert number of users returned
        // Assert users are mapped to UserShortProfileDTO using ModelMapper
        actualUserPage.getData()
                .forEach(user -> assertThat(user).isInstanceOf(UserShortProfileDTO.class));
    }

    @Test
    void getTicketsByUser() {
        //Given
        UUID userId = UUID.randomUUID();
        Ticket ticket = Ticket.builder()
                .activityTimeFrame(ActivityTimeFrame.builder().activityDate(ActivityDate.builder().activity(TravelActivity.builder().build()).build()).build())
                .build();
        List<Ticket> tickets = Arrays.asList(ticket, ticket);

        User user = User.builder()
                .id(userId)
                .tickets(tickets)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //When
        List<TicketResponseDTO> response = underTest.getTicketsByUser(userId);

        //Then
        assertThat(response).isNotNull();
        response.forEach(ticketResponse -> assertThat(ticketResponse).isInstanceOf(TicketResponseDTO.class));
    }
}