package com.hcmut.travogue.repository;

import com.hcmut.travogue.model.entity.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    UserRepository underTest;

    @BeforeEach
    void setUp() {
        //given
        User user1 = new User(); // Assuming User creation logic is tested elsewhere
        user1.setEmail("testUser1@gmail.com");
        underTest.save(user1); // Persist user1

        User user2 = new User(); // Assuming User creation logic is tested elsewhere
        user2.setEmail("testUser2@gmail.com");
        underTest.save(user2); // Persist user1
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindUserByExistingEmail() {
        //when
        Optional<User> userByEmail = underTest.findByEmail("testUser1@gmail.com");
        //then
        assertThat(userByEmail).isPresent();
        assertThat(userByEmail.get().getEmail()).isEqualTo("testUser1@gmail.com");
    }

    @Test
    void shouldReturnEmptyOptionalWhenFindByEmailIsNotFound() {
        //when
        Optional<User> userByEmail = underTest.findByEmail("notExistEmail@gmail.com");
        //then
        assertThat(userByEmail).isNotPresent();
    }

    @Test
    void shouldReturnTrueWhenExistsByEmailWithEmailExists() {
        //when
        //then
        assertThat(underTest.existsByEmail("testUser1@gmail.com")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenExistsByEmailWithEmailNotFound() {
        //when
        //then
        assertThat(underTest.existsByEmail("notExistEmail@gmail.com")).isFalse();
    }

    @Test
    void shouldFindPageUsersByEmptyKeyword() {
        //given
        String keyword = "";
        int pageNumber = 0;
        int pageSize = 4;
        String sortField = "id";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());
        //when
        Page<User> pageUserResponse = underTest.findPageUsers(keyword, pageable);
        //then
        assertThat(pageUserResponse.getContent()).size().isEqualTo(2);
        assertThat(pageUserResponse.getNumber()).isEqualTo(pageNumber);
        assertThat(pageUserResponse.getSize()).isEqualTo(pageSize);
    }

    @Test
    void shouldFindPageUsersByKeyword() {
        //given
        String keyword = "User1";
        int pageNumber = 0;
        int pageSize = 4;
        String sortField = "id";
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).ascending());
        //when
        Page<User> pageUserResponse = underTest.findPageUsers(keyword, pageable);
        //then
        assertThat(pageUserResponse.getContent()).size().isEqualTo(1);
        assertThat(pageUserResponse.getNumber()).isEqualTo(pageNumber);
        assertThat(pageUserResponse.getSize()).isEqualTo(pageSize);
        assertThat(pageUserResponse.getContent())
                .allSatisfy(user -> assertThat(user.getEmail() + ' ' + user.getFirstName() + ' ' + user.getLastName())
                        .containsIgnoringCase("user1"));
    }
}