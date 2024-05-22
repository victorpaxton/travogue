package com.hcmut.travogue.repository.TravelActivity;

import com.hcmut.travogue.model.entity.TravelActivity.ActivityDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ActivityDateRepository extends JpaRepository<ActivityDate, UUID> {
    boolean existsByDate(Date date);

    ActivityDate findByDate(Date date);
}
