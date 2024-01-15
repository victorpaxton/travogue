package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    List<City> findFirst8ByOrderByTravelPointDesc();

    @Query(value = "SELECT * FROM city AS c " +
            "WHERE c.name " +
            "ILIKE %:keyword%", nativeQuery = true)
    Page<City> findPageCities(@Param("keyword") String keyword, Pageable pageable);
}
