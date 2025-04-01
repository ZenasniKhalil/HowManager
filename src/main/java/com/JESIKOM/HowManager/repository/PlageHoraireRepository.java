package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.JourSemaine;
import com.JESIKOM.HowManager.models.PlageHoraire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlageHoraireRepository extends JpaRepository<PlageHoraire, Long> {
    void deletePlageHoraireById(Long id);

    List<PlageHoraire> findPlageHoraireByJourDebut(JourSemaine jourDebut);
}
