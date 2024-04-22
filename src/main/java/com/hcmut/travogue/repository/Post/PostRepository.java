package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByUser_Id(UUID userId);
}
