package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {
    boolean existsByUser_IdAndPost_Id(UUID userId, UUID postId);

    Optional<PostLike> findByUser_IdAndPost_Id(UUID userId, UUID postId);
}
