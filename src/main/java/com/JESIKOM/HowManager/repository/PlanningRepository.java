package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
}
