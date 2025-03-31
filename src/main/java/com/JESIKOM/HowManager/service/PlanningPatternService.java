package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.PlanningPattern;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import com.JESIKOM.HowManager.repository.PlanningPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlanningPatternService {


    @Autowired
    private PlanningPatternRepository planningPatternRepository;

    @Autowired
    private PlageHoraireService plageHoraireService;

    @Autowired
    private PlageHoraireRepository plageHoraireRepository;

    PlanningPattern   addPlanningPattern(PlanningPattern pP) {return null;}

    void deletePlanningPattern(Long pid) {}

    PlanningPattern   updateSafePlanningPattern(long pPid, PlanningPattern updatedPp) {return null;}

    PlageHoraire   addIntoPlanningPlageHoraire(long pPid, long pHid) {return null;
    }

    void deletePlageHorairefromPlanningPattern(Long PPid,long PHid) {}

    boolean isConflictingPlageHoraireinPlanningPattern(Long PPid,long PHid) {return true;}

    PlanningPattern updatePlanningPattern(long pid, PlanningPattern updatedPp) {return null;}

    float computeNombreHeuresPlanning(long pPid){return -1;}

    Map<TypeMajoration,Float> computeNbHeuresWithMajoration(){return null;}




}
