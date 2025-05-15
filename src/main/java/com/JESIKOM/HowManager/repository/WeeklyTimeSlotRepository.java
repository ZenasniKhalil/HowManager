package com.JESIKOM.HowManager.repository;
import com.JESIKOM.HowManager.models.Personnel;
import java.util.List;

import com.JESIKOM.HowManager.models.WeeklyTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyTimeSlotRepository extends JpaRepository<WeeklyTimeSlot, Long> {
    List<WeeklyTimeSlot> findByPersonnel(Personnel personnel);
    List<WeeklyTimeSlot> findByPersonnelMatricule(Long matricule);

}
