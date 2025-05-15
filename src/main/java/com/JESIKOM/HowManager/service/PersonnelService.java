package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.models.Planning;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PersonnelRepository;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PlanningRepository planningRepository;

    public Map<TypeMajoration, Float> getHeuresEffectuees(int year, int month) {
        return null; // à implémenter plus tard
    }

    public Personnel addPersonnel(Personnel p) {
        Personnel savedPersonnel = personnelRepository.save(p);

        Planning planning = new Planning();
        planning.setPersonnel(savedPersonnel);
        planning.setAnnee(LocalDate.now().getYear());
        planning.setSemaine(LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
        planning.setNote("Nouveau personnel enregistré");

        planningRepository.save(planning);

        return savedPersonnel;
    }

    public List<Personnel> getAllPersonnel() {
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getPersonnelByMatricule(Long matricule) {
        return personnelRepository.findByMatricule(matricule);
    }

    public List<Personnel> getPersonnelsBySuperieurHerarchique(Long matriculeSupHierarchique) {
        return personnelRepository.findBySuperieurHierarchique_Matricule(matriculeSupHierarchique);
    }

    public List<Personnel> getPersonnelsByPoste(String poste) {
        return personnelRepository.findByPoste(poste);
    }

    public List<Personnel> getPersonnelsByGenre(String genre) {
        return personnelRepository.findByGenre(genre);
    }

    public List<Personnel> getPersonnelsByNomContains(String nom) {
        return personnelRepository.findByNomContains(nom);
    }

    public List<Personnel> getPersonnelsByPrenomContains(String prenom) {
        return personnelRepository.findByPrenomContains(prenom);
    }

    public List<Personnel> getPersonnelsStatusContains(String status) {
        return personnelRepository.findByStatusContains(status);
    }

    public Personnel updatePersonnelByMatricule(Long matricule, Personnel updatedP) {
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
            p.setSuperieurHierarchique(updatedP.getSuperieurHierarchique());
            p.setPersonnelDocuments(updatedP.getPersonnelDocuments());
            p.setPlanningPatterns(updatedP.getPlanningPatterns());
            p.setPlannings(updatedP.getPlannings());
            return personnelRepository.save(p);
        }).orElse(null);
    }

    public void deletePersonnelByMatricule(Long matricule) {
        personnelRepository.deleteByMatricule(matricule);
    }

    private boolean areConflictingPlanningOfPersonnel(Personnel perso) {
        List<Planning> tested = new LinkedList<>();
        for (Planning p : perso.getPlannings()) {
            for (Planning test : tested) {
                if (p.getAnnee() == test.getAnnee() && p.getSemaine() == test.getSemaine())
                    return true;
            }
            tested.add(p);
        }
        return false;
    }
}
