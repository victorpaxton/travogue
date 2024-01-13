package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityCategoryRepository extends JpaRepository<ActivityCategory, UUID> {
    List<ActivityCategory> findByParentCategoryIsNull();

    @Query(value = "WITH RECURSIVE category_tree AS (\n" +
            "    SELECT ac.id, ac.parent_id\n" +
            "    FROM activity_category ac\n" +
            "    WHERE ac.id = :categoryId\n" +
            "    UNION ALL\n" +
            "    SELECT ac.id, ac.parent_id\n" +
            "    FROM activity_category ac\n" +
            "    JOIN category_tree ct ON ac.parent_id = ct.id\n" +
            ")\n" +
            "SELECT id\n" +
            "FROM category_tree;", nativeQuery = true)
    List<UUID> findChildCategoryIds(@Param("categoryId") UUID categoryId);
}
