package com.hcmut.travogue.repository.Post;

import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.Post.PostUserTagged;
import com.hcmut.travogue.model.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostUserTaggedRepository extends JpaRepository<PostUserTagged, UUID> {
    List<PostUserTagged> findByPost_Id(UUID postId);

    List<PostOnly> findAllByUser_Id(UUID userTaggedId);

    interface PostOnly {
        PostId getPost();
        interface PostId {
            UUID getId();
        }
    }
}


