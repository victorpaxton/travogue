package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, UUID> {
    List<ActivityCategory> findByParentCategoryIsNull();
}
