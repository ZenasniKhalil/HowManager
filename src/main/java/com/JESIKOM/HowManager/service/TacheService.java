package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Tache;

import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;

@Service
public class TacheService {



    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private WeeklyTimeSlotService weeklyTimeSlotService;

    public Optional<Tache> getTacheById(Long pHid){
        return tacheRepository.findById(pHid);
    }
    public List<Tache> getAllTache(){
        return tacheRepository.findAll();
    }
    public List<Tache> getTacheByDateDebut(LocalDate date){
        return tacheRepository.findTacheByDateDebut(date);
    }
    public List<Tache> getTacheByDateFin(LocalDate date){
        return tacheRepository.findTacheByDateFin(date);
    }

    Tache addTache(Tache pH)  {
        return tacheRepository.save(pH);
    }



    void deleteTache(long pHid) {
        tacheRepository.deleteTacheById(pHid);
    }

    Tache updateTache(long tid, Tache updatedPh){
        return tacheRepository.findById(tid).map(t -> {
            t.setPlage(updatedPh.getPlage());
            t.setPoste(updatedPh.getPoste());
            t.setLieu(updatedPh.getLieu());
            t.setNotes(updatedPh.getNotes());
            return tacheRepository.save(t);
        }).orElse(null);

    }

    float computeDuree(Tache t){
        return weeklyTimeSlotService.computeTime(t.getPlage());
    }

    public   boolean overlapsWith(Tache t1, Tache t2){
        return weeklyTimeSlotService.overlapsWith(t1.getPlage(), t2.getPlage());
    }

    private boolean isValid(Tache t){
        return (t.getDateDebut().getDayOfWeek()==(t.getPlage().getStartDay())&&
                t.getDateFin().getDayOfWeek()==(t.getPlage().getEndDay()))
        ;
    }

    public int getWeekOfTache(Tache t){
        return  LocalDateService.getWeekOfDate(t.getDateDebut());
    }
    public int getWeekBasedYearOfTache(Tache t) {
        return LocalDateService.getWeekBasedYearOfDate(t.getDateDebut());
    }




    Map<TypeMajoration,Float> computeHeuresMajorees(Tache t){ return new HashMap<>();}


    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

}




