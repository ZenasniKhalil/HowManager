package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.PlanningPattern;
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
    @Autowired
    private PlanningPatternService planningPatternService;
    @Autowired
    private PlanningService planningService;


    Map<TypeMajoration,Float> getHeuresEffectuees(int year, int month) {
        return null;
    }
    public Personnel addPersonnel(Personnel p ){
        return null;
    }
    public Optional<Personnel> getPersonnelByMatricule(Long matricule){
        return personnelRepository.findByMatricule(matricule);
    }

    public List<Personnel> getPersonnelsBySuperieurHerarchique(Long matriculeSupHierarchique){
        return personnelRepository.findBySuperieurHierarchique_Matricule(matriculeSupHierarchique);
    }

    public List<Personnel> getPersonnelsByPoste(String poste){
        return personnelRepository.findByPoste(poste);}

    public List<Personnel> getPersonnelsByGenre(String genre){
        return personnelRepository.findByGenre(genre);
    }

    public List<Personnel> getPersonnelsByNomContains(String nom){
        return personnelRepository.findByNomContains(nom);
    }

    public List<Personnel> getPersonnelsByPrenomContains(String prenom){
        return personnelRepository.findByPrenomContains(prenom);
    }

    public List<Personnel> getPersonnelsStatusContains(String status){
        return personnelRepository.findByStatusContains(status);}

    public Personnel updatePersonnelByMatricule(Long matricule, Personnel updatedP){

        return personnelRepository.findByMatricule(matricule).map(p -> {

            p.setNom(updatedP.getNom());
            p.setPrenom(updatedP.getPrenom());
            p.setGenre(updatedP.getGenre());
            p.setNationalite(updatedP.getNationalite());
            p.setPhone(updatedP.getPhone());
            p.setEmail(updatedP.getEmail());
            p.setAdresse(updatedP.getAdresse());
            p.setPoste(updatedP.getPoste());
            p.setNotes(updatedP.getNotes());
            p.setTauxHoraire(updatedP.getTauxHoraire());
            p.setMajorations(updatedP.getMajorations());
            p.setNbHeureMois(updatedP.getNbHeureMois());
            p.setNbHeureMois(updatedP.getNbHeureMois());
            p.setSuperieurHierarchique(updatedP.getSuperieurHierarchique());
            p.setPersonnelDocuments(updatedP.getPersonnelDocuments());
            p.setPlanningPatterns(updatedP.getPlanningPatterns());
            p.setPlannings(updatedP.getPlannings());
            return personnelRepository.save(p);
        }).orElse(null);
    }

    public void deletePersonnelByMatricule(Long matricule){
        personnelRepository.deleteByMatricule(matricule);
    }
    private boolean areConflictingPatternsofPersonnel(Personnel p){
        return false;

    }
    private boolean areConflictingPatternsOfPersonnelByMatricule(Long pid) throws IllegalArgumentException{
        Optional<Personnel> p = personnelRepository.findByMatricule(pid);
        if(p.isPresent()){
            return areConflictingPatternsofPersonnel(p.get());
        }
        else throw new IllegalArgumentException("Mauvais ID");

    }
}
