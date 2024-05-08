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

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.activity_category_id IN (:categoryIds) " +
            "ORDER BY t.travel_point DESC " +
            "LIMIT 10", nativeQuery = true)
    List<TravelActivity> findFirst10ByActivityCategory_IdOrderByTravelPointDesc(@Param("categoryIds") List<UUID> categoryIds);

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.activity_category_id IN (:categoryIds) " +
            "AND CONCAT(t.activity_name, ' ', t.tags, ' ', t.personal_options, ' ', t.general_price) " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<TravelActivity> findPageTravelActivitiesByCategories(@Param("categoryIds") List<UUID> categoryIds , @Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.city_id = :cityId " +
            "AND CONCAT(t.activity_name, ' ', t.tags, ' ', t.personal_options, ' ', t.general_price) " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<TravelActivity> findPageTravelActivitiesByCity(@Param("cityId") UUID cityId, @Param("keyword") String keyword, Pageable pageable);

    Page<TravelActivity> findByHost_Id(UUID hostId, Pageable pageable);

    int countAllByHost_Id(UUID hostId);

    @Query(value = "SELECT COUNT(DISTINCT t.city_id) FROM travel_activity AS t WHERE t.host_id = :hostId", nativeQuery = true)
    int countDistinctCityByHostId(@Param("hostId") UUID hostId);

    List<TravelActivity> findFirst10ByHost_IdOrderByAverageRatingDesc(UUID hostId);

    @Query(value = "SELECT \n" +
            "            (COALESCE(SUM(activity_comment.rating), 0) + :newRating) /\n" +
            "            (COUNT(activity_comment.rating) + 1)\n" +
            "        FROM activity_comment\n" +
            "        WHERE activity_comment.travel_activity_id = :activityId", nativeQuery = true)
    Double calcAvgRating(@Param("activityId") UUID activityId, @Param("newRating") double newRating);

    @Query(value = "SELECT * FROM travel_activity AS t " +
            "WHERE t.activity_name " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<TravelActivity> findPageActivities(@Param("keyword") String keyword, Pageable pageable);
}
