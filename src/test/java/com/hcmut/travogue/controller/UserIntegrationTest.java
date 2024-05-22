package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Auth.AuthenticationResponseDTO;
import com.hcmut.travogue.model.dto.Auth.RegisterV2;
import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.User.UserProfileDTO;
import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.User.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    public static final String API_USERS_PATH = "/users";

    public static int count = 1;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

//    @Test
//    void canEstablishConnection() {
//        assertThat(postgreSQLContainer.isCreated()).isTrue();
//        assertThat(postgreSQLContainer.isRunning()).isTrue();
//    }

    @Test
    void testGetUserSuccess() {
        //given
        RegisterV2 registerRequest = new RegisterV2("name", "email" + count++ + "@gmail.com", "Abc@1234");
        ResponseEntity<ResponseModel<AuthenticationResponseDTO>> registerResponse = testRestTemplate.exchange(
                "/auth/register-v2",
                HttpMethod.POST,
                new HttpEntity<>(registerRequest),
                new ParameterizedTypeReference<ResponseModel<AuthenticationResponseDTO>>() {
                }
        );
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        UUID userId = Objects.requireNonNull(registerResponse.getBody()).getData().getUser().getId();
        String accessToken = registerResponse.getBody().getData().getTokens().getAccess().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        //when
        ResponseEntity<ResponseModel<UserProfileDTO>> userByIdResponse = testRestTemplate.exchange(
                API_USERS_PATH + "/" + userId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );
        //then
        assertThat(userByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(userByIdResponse.getBody()).isSuccess()).isTrue();
        UserProfileDTO userProfileDTO = Objects.requireNonNull(userByIdResponse.getBody()).getData();
        assertThat(userProfileDTO.getEmail()).isEqualTo(registerRequest.getEmail());
        assertThat(userProfileDTO.getLastName()).isEqualTo(registerRequest.getName());
    }

    @Test
    void testgetPageUsers() {
        //given
        RegisterV2 registerRequest = new RegisterV2(
                "name",
                "email" + count++ + "@gmail.com",
                "Abc@1234"
        );

        ResponseEntity<ResponseModel<AuthenticationResponseDTO>> registerResponse = testRestTemplate.exchange(
                "/auth/register-v2",
                HttpMethod.POST,
                new HttpEntity<>(registerRequest),
                new ParameterizedTypeReference<ResponseModel<AuthenticationResponseDTO>>() {
                }
        );
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        UUID userId = Objects.requireNonNull(registerResponse.getBody()).getData().getUser().getId();
        String accessToken = registerResponse.getBody().getData().getTokens().getAccess().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        //when
        ResponseEntity<ResponseModel<PageResponse<UserShortProfileDTO>>> allUser = testRestTemplate.exchange(
                API_USERS_PATH,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(allUser.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(allUser.getBody()).isSuccess()).isTrue();
        assertThat(allUser.getBody().getData().getTotalItems()).isEqualTo(2);
    }

//    @Test
//    void getTicketsByUser() {
//
//
//    }
}