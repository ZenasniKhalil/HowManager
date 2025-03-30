package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheService extends JpaRepository<Tache, Long> {
}
