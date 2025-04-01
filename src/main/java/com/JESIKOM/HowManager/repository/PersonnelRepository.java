package com.JESIKOM.HowManager.repository;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    Optional<Personnel> findByMatricule(Long matricule);

    List<Personnel> findBySuperieurHierarchique_Matricule(Long superieurHierarchiqueMatricule);

    List<Personnel> findByPoste(String poste);

    List<Personnel> findByGenre(String genre);

    List<Personnel> findByNomContains(String nom);

    List<Personnel> findByPrenom(String prenom);

    List<Personnel> findByPrenomContains(String prenom);

    List<Personnel> findByStatusContains(String status);

    void deleteByMatricule(Long matricule);
}
