package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.Post.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, UUID> {
    Optional<PostComment> findFirstByPost_IdOrderByUpdatedAtDesc(UUID postId);
}
