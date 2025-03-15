package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.TypeLogement;
//import org.hibernate.annotations.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogementRepository extends JpaRepository<Logement, Integer> {
    //List<Logement>findLogementByCapacite(Integer capacite);

    List<Logement>findLogementByCapaciteAndDisponibleTrue(Integer capacite);

    List<Logement>findLogementByPrixOrderByPrixAsc(Double prix);

    List<Logement>findLogementByPrixAndDisponibleTrueOrderByPrixAsc(Double prix);

    //List<Logement>findLogementByDisponible(Boolean disponible);

    List<Logement> findLogementByType(TypeLogement type);
}
