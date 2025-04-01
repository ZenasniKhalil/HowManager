package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.models.Tache;
import com.JESIKOM.HowManager.repository.TacheRepository;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;

    Optional<Planning> getPlanningById(Long id) {
        return planningRepository.findById(id);
    }

    List<Planning> getAllPlannings() {
        return planningRepository.findAll();
    }

    Planning addPlanning(Planning pP) {
        return planningRepository.save(pP);
    }

    void deletePlanning(Long pid) {
        planningRepository.deleteById(pid);
    }

    Planning   updatePlanning(long pid, Planning updatedP) throws IllegalArgumentException {
        if (!isConflictingPlanning(updatedP)) {
            return planningRepository.findById(pid).map(p->{
                p.setPersonnel(updatedP.getPersonnel());
                p.setAnnee(updatedP.getAnnee());
                p.setTaches(updatedP.getTaches());
                p.setNote(updatedP.getNote());
                return planningRepository.save(p);
            }).orElse(null);
        }
        else throw new IllegalArgumentException("Conflit dans updatedPlanning" );
    }


    private boolean isConflictingPlanning(Planning p) {return false;}




    float computeNombreHeuresPlanning(long pid){return -1;}

    Map<TypeMajoration,Float> computeNbHeuresWithMajoration(){return null;}
}
