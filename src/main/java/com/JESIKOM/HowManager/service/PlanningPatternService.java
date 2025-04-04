package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.repository.PlanningPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if (isConflictingPlanningPattern(pP))
            throw new IllegalArgumentException("Conflit dans le planningPattern" );

        return planningPatternRepository.save(pP);
    }

    void deletePlanningPattern(Long pid) {
        planningPatternRepository.deleteById(pid);
    }

    PlanningPattern   updatePlanningPattern(long pPid, PlanningPattern updatedPp) throws IllegalArgumentException {
        if (isConflictingPlanningPattern(updatedPp))
            throw new IllegalArgumentException("Conflit dans updatedPlanningPattern" );

        return planningPatternRepository.findById(pPid).map(pp->{
            pp.setNom(updatedPp.getNom());
            pp.setDescription(updatedPp.getDescription());
            pp.setPlagesHoraires(updatedPp.getPlagesHoraires());
            pp.setNote(updatedPp.getNote());
            return planningPatternRepository.save(pp);
        }).orElse(null);
    }


     private boolean isConflictingPlanningPattern(PlanningPattern pp) {
         List<PlageHoraire> phtested = new LinkedList<>();
         for (PlageHoraire ph : pp.getPlagesHoraires()) {
             for (PlageHoraire ph2 : phtested) {
                 if (plageHoraireService.overlapsWith(ph, ph2)) {
                     return true;
                 }
             }
                 phtested.add(ph);
             }
         return false;
     }

    float computeNombreHeuresPlanningPattern(PlanningPattern pt){
        float duree= 0;
        for( PlageHoraire ph : pt.getPlagesHoraires()) {
            duree+=plageHoraireService.computeDuree(ph);

        }
        return duree;
    }



    Map<TypeMajoration,Float> computeNbHeuresWithMajoration(PlanningPattern pt){
        Map<TypeMajoration,Float> res =new HashMap<>();
        for( PlageHoraire ph : pt.getPlagesHoraires()) {
            Map<TypeMajoration,Float> mapToAdd = plageHoraireService.computeHeuresMajorees(ph);
            mapToAdd.forEach((k, v) -> res.merge(k, v, Float::sum));
        }
        return res;

    }




}

