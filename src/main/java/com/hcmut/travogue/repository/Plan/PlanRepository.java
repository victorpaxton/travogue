package com.hcmut.travogue.repository.Plan;

import com.hcmut.travogue.model.entity.Plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, UUID> {
    List<Plan> findAllByUser_Id(UUID userId);
}
