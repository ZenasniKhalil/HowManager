package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class PlageHoraireService {
    @Autowired
    private PlageHoraireRepository plageHoraireRepository;
    @Autowired
    private WeeklyTimeSlotService weeklyTimeSlotService;

    public Optional<PlageHoraire> getPlageHoraireById(Long pHid){
        return plageHoraireRepository.findById(pHid);
    }
    public List<PlageHoraire> getAllPlageHoraire(){
        return plageHoraireRepository.findAll();
    }
    public List<PlageHoraire> getAllPlageHoraireByJourSemaine(DayOfWeek jourSemaine){
        return plageHoraireRepository.findPlageHoraireByPlage_StartDay(jourSemaine);
    }

    PlageHoraire addPlageHoraire(PlageHoraire pH) throws IllegalArgumentException {
        return plageHoraireRepository.save(pH);
        }



    void deletePlageHoraire(long pHid) {
        plageHoraireRepository.deletePlageHoraireById(pHid);
    }

    PlageHoraire updatePlageHoraire(long pHid, PlageHoraire updatedPh) throws IllegalArgumentException {
        return plageHoraireRepository.findById(pHid).map(ph -> {
                ph.setPlage(updatedPh.getPlage());
                ph.setPoste(updatedPh.getPoste());
                ph.setLieu(updatedPh.getLieu());
                ph.setNotes(updatedPh.getNotes());
                return plageHoraireRepository.save(ph);
            }).orElse(null);

    }




    float computeDuree(PlageHoraire pH) throws IllegalArgumentException {

        return weeklyTimeSlotService.computeTime(pH.getPlage());

    }

    public boolean overlapsWith(PlageHoraire ph1, PlageHoraire ph2){
        return weeklyTimeSlotService.overlapsWith(ph1.getPlage(), ph2.getPlage());
    }



    Map<TypeMajoration,Float> computeHeuresMajorees(PlageHoraire pH){ return new HashMap<>();}


}
