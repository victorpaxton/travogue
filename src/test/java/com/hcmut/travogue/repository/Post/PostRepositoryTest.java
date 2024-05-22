package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.UserRepository;
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

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    PostRepository underTest;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findAllByUser_IdOrIdInOrderByUpdatedAtDesc() {
        //given
        User user1 = userRepository.save(User.builder().email("user1@gmail.com").build());
        Post post1 = underTest.save(Post.builder()
                .caption("post1")
                .user(user1)
                .build());

        User user2 = userRepository.save(User.builder().email("user2@gmail.com").build());
        Post post2 = underTest.save(Post.builder()
                .caption("post2")
                .user(user2)
                .build());

        Post post3 = underTest.save(Post.builder()
                .caption("post3")
                .user(user2)
                .build());

        //when
        List<UUID> postTagged = Collections.singletonList(post3.getId());
        List<Post> postResponse = underTest.findAllByUser_IdOrIdInOrderByUpdatedAtDesc(user1.getId(), postTagged);

        //then
        assertThat(postResponse).hasSize(2)
            .containsExactlyInAnyOrder(post1, post3);
    }

    @Test
    void countAllByUser_Id() {
        //given
        User user1 = userRepository.save(User.builder().email("user1@gmail.com").build());
        Post post1 = underTest.save(Post.builder()
                .caption("post1")
                .user(user1)
                .build());

        User user2 = userRepository.save(User.builder().email("user2@gmail.com").build());
        Post post2 = underTest.save(Post.builder()
                .caption("post2")
                .user(user2)
                .build());

        Post post3 = underTest.save(Post.builder()
                .caption("post3")
                .user(user2)
                .build());

        //when
        int res1 = underTest.countAllByUser_Id(user1.getId());
        int res2 = underTest.countAllByUser_Id(user2.getId());

        //then
        assertThat(res1).isEqualTo(1);
        assertThat(res2).isEqualTo(2);
    }

    @Test
    void findAllByUser_IdInOrIdIn() {
        //given
        User user1 = userRepository.save(User.builder().email("user1@gmail.com").build());
        Post post1 = underTest.save(Post.builder()
                .caption("post1")
                .user(user1)
                .build());

        User user2 = userRepository.save(User.builder().email("user2@gmail.com").build());
        Post post2 = underTest.save(Post.builder()
                .caption("post2")
                .user(user2)
                .build());

        Post post3 = underTest.save(Post.builder()
                .caption("post3")
                .user(user2)
                .build());

        User user3 = userRepository.save(User.builder().email("user3@gmail.com").build());
        Post post4 = underTest.save(Post.builder()
                .caption("post4")
                .user(user3)
                .build());

        //when
        List<UUID> userIds = Arrays.asList(user1.getId(), user2.getId());
        List<UUID> postTagged = Collections.singletonList(post4.getId());
        Page<Post> response = underTest.findAllByUser_IdInOrIdIn(userIds, postTagged,
                    PageRequest.of(0, 10, Sort.by("caption").descending())
                );

        //then
        assertThat(response.getContent()).hasSize(4);
        assertThat(response.getContent()).containsExactly(post4, post3, post2, post1);
    }
}