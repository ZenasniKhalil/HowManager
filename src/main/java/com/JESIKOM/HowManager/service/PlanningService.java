package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.models.Tache;
import com.JESIKOM.HowManager.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;


    @Autowired
    private TacheService tacheService;

    Optional<Planning> getPlanningById(Long id) {
        return planningRepository.findById(id);
    }

    List<Planning> getAllPlannings() {
        return planningRepository.findAll();
    }

    Planning addPlanning(Planning pP) throws IllegalArgumentException {
        if (isConflictingPlanning(pP)) {
            throw new IllegalArgumentException("Conflit dans le planning");
        }
        else
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


    /*
        Check le chevauchement de taches,
        l'egalité de semaine et l'egalité de l'année basé sur la semaine
        ( semaine 1 du calendrier = semaine où le premier jeudi de janvier est dedans)
     */
    private boolean isConflictingPlanning(Planning p) {
        List<Tache> tachesTested = new LinkedList<>();

        for (Tache t : p.getTaches()) {
            if (tacheService.getWeekOfTache(t)!=p.getSemaine() || tacheService.getWeekBasedYearOfTache(t)!=p.getSemaine()) {
                System.out.println(  "semaine p :"+p.getSemaine()+"semaine t"+tacheService.getWeekOfTache(t));
                return true;
            }
            for (Tache t2 : tachesTested) {
                if (tacheService.overlapsWith(t, t2))
                    return true;
            }
            tachesTested.add(t);
        }
        return false;
    }

    float computeNombreHeuresPlanning(Planning p){
        float duree= 0;
        for( Tache t : p.getTaches()) {
            duree+=tacheService.computeDuree(t);

        }
        return duree;
    }

Map<TypeMajoration,Float> computeNbHeuresWithMajoration(Planning p){
    Map<TypeMajoration,Float> res =new HashMap<>();
    for( Tache t : p.getTaches()) {
        Map<TypeMajoration,Float> mapToAdd = tacheService.computeHeuresMajorees(t);
        mapToAdd.forEach((k, v) -> res.merge(k, v, Float::sum));
    }
    return res;

    }
    public Planning createPlanningFromPlanningPattern(LocalDate MondayDate, PlanningPattern pp) {
        Planning planning = new Planning();
        for (PlageHoraire ph : pp.getPlagesHoraires()) {
            Tache t = new Tache();
            t.setPoste(ph.getPoste());
            t.setLieu(ph.getLieu());
            t.setPlage(ph.getPlage());
            t.setStatus(StatusTache.PLANIFIEE);
            t.setDateDebut(MondayDate.plusDays((ph.getPlage().getStartDay().getValue() - 1)));
            t.setDateFin(MondayDate.plusDays(ph.getPlage().getEndDay().getValue() - 1));
            t.setNotes(ph.getNotes());
            t=tacheService.addTache(t);
            planning.getTaches().add(t);
        }
    return planning;
    }
    public List<Planning> listerTous() {
        return planningRepository.findAll();
    }
}


