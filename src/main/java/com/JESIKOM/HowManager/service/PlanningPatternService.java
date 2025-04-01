package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.PlanningPattern;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import com.JESIKOM.HowManager.repository.PlanningPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanningPatternService {


    @Autowired
    private PlanningPatternRepository planningPatternRepository;

    @Autowired
    private PlageHoraireService plageHoraireService;

    Optional<PlanningPattern> getPlanningPatternById(Long id) {
        return planningPatternRepository.findById(id);
    }

    List<PlanningPattern> getAllPlanningPatterns() {
        return planningPatternRepository.findAll();
    }

    List<PlanningPattern> getPlanningPatternsByDescContains(String description) {
        return planningPatternRepository.findByDescriptionContainsIgnoreCase(description);
    }


    PlanningPattern   addPlanningPattern(PlanningPattern pP) {
        return planningPatternRepository.save(pP);
    }

    void deletePlanningPattern(Long pid) {
        planningPatternRepository.deleteById(pid);
    }

    PlanningPattern   updatePlanningPattern(long pPid, PlanningPattern updatedPp) throws IllegalArgumentException {
        if (!isConflictingPlanningPattern(updatedPp)) {
            return planningPatternRepository.findById(pPid).map(pp->{
                pp.setNom(updatedPp.getNom());
                pp.setDescription(updatedPp.getDescription());
                pp.setPlagesHoraires(updatedPp.getPlagesHoraires());
                pp.setNote(updatedPp.getNote());
                return planningPatternRepository.save(pp);
            }).orElse(null);
        }
        else throw new IllegalArgumentException("Conflit dans updatedPlanningPattern" );
    }


     private boolean isConflictingPlanningPattern(PlanningPattern pp) {return false;}



    float computeNombreHeuresPlanning(long pPid){return -1;}

    Map<TypeMajoration,Float> computeNbHeuresWithMajoration(){return null;}




}

