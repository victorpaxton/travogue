package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.PostUserTagged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostUserTaggedRepository extends JpaRepository<PostUserTagged, UUID> {
    List<PostUserTagged> findByPost_Id(UUID postId);
}
