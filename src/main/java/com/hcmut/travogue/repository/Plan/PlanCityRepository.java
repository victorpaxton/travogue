package com.hcmut.travogue.repository.Plan;

import com.hcmut.travogue.model.entity.Plan.PlanCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanCityRepository extends JpaRepository<PlanCity, UUID> {
}
