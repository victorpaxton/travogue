package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TravelActivityRepository extends JpaRepository<TravelActivity, UUID> {
    List<TravelActivity> findFirst10ByOrderByTravelPointDesc();

    List<TravelActivity> findFirst10ByActivityCategory_IdOrderByTravelPointDesc(UUID categoryId);

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.activity_category_id IN (:categoryIds) " +
            "AND CONCAT(t.activity_name, ' ', t.tags, ' ', t.personalOptions, ' ', t.generalPrice) " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<TravelActivity> findPageTravelActivitiesByCategories(@Param("categoryIds") List<UUID> categoryIds , @Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.city_id = :cityId " +
            "AND CONCAT(t.activity_name, ' ', t.tags, ' ', t.personalOptions, ' ', t.generalPrice) " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<TravelActivity> findPageTravelActivitiesByCity(@Param("cityId") UUID cityId, @Param("keyword") String keyword, Pageable pageable);
}
