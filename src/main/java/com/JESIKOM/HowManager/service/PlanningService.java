package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Tache;
import com.JESIKOM.HowManager.models.Planning;
import com.JESIKOM.HowManager.models.Tache;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.TacheRepository;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository PlanningRepository;

    @Autowired
    private TacheService TacheService;

    @Autowired
    private TacheRepository TacheRepository;

    Planning getPlanningById(Long pid){return null;}

    Planning addPlanning(Planning p) {return null;}

    void deletePlanning(Long pid) {}

    Planning updateSafePlanning(long pid, Planning updatedP) {return null;}

    Tache   addIntoPlanningTache(long pPid, long pHid) {return null;
    }

    void deleteTachefromPlanning(Long PPid,long PHid) {}

    boolean isConflictingTacheinPlanning(Long PPid,long PHid) {return true;}



    float computeNombreHeuresPlanning(long pid){return -1;}

    Map<TypeMajoration,Float> computeNbHeuresWithMajoration(){return null;}
}
