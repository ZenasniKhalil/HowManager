package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;


    Map<TypeMajoration,Float> getHeuresEffectuees(int year, int month) {
        return null;
    }
    public Personnel addPersonnel(Personnel p ){
        return null;
    }
    public Optional<Personnel> getPersonnelByMatricule(Long matricule){return null;}

    public List<Personnel> getPersonnelsBySuperieurHerarchique(Long matriculeSupHierarchique){return null;}

    public List<Personnel> getPersonnelsByPoste(String poste){return null;}

    public List<Personnel> getPersonnelsByGenre(String genre){return null;}

    public List<Personnel> getPersonnelsByNomContains(String nom){return null;}

    public List<Personnel> getPersonnelsByPrenomContains(String prenom){return null;}

    public List<Personnel> getPersonnelsStatusContains(String status){return null;}

    public Personnel updatePersonnel(Long matricule, Personnel pUpdated){
        return null;
    }

    public void deletePersonnel(Long matricule){

    }
}
