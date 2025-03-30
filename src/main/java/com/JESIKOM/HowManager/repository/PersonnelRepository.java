package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
}
