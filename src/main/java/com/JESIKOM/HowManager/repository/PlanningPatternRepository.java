package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.PlanningPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanningPatternRepository extends JpaRepository<PlanningPattern, Long> {
    List<PlanningPattern> findByDescriptionContainsIgnoreCase(String description);
}
