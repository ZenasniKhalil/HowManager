package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findTacheByDateDebut(LocalDate dateDebut);

    List<Tache> findTacheByDateFin(LocalDate dateFin);

    void deleteTacheById(Long id);
}
