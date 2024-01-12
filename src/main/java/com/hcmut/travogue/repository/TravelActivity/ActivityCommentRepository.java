package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityCommentRepository extends JpaRepository<ActivityComment, UUID> {

    List<ActivityComment> findByTravelActivityIdOrderByCreatedAtDesc(UUID activityId);

}
