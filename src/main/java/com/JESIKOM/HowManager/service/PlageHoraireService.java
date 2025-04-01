package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.JourSemaine;
import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlageHoraireService {
    @Autowired
    PlageHoraireRepository plageHoraireRepository;

    public Optional<PlageHoraire> getPlageHoraireById(Long pHid){
        return plageHoraireRepository.findById(pHid);
    }
    public List<PlageHoraire> getAllPlageHoraire(){
        return plageHoraireRepository.findAll();
    }
    public List<PlageHoraire> getAllPlageHoraireByJourSemaine(JourSemaine jourSemaine){
        return plageHoraireRepository.findPlageHoraireByJourDebut(jourSemaine);
    }

    PlageHoraire addPlageHoraire(PlageHoraire pH) {
            return plageHoraireRepository.save(pH);
        }



    void deletePlageHoraire(long pHid) {
        plageHoraireRepository.deletePlageHoraireById(pHid);
    }

    PlageHoraire updatePlageHoraire(long pHid, PlageHoraire updatedPh) {
            return plageHoraireRepository.findById(pHid).map(ph -> {
                ph.setJourDebut(updatedPh.getJourDebut());
                ph.setHeureDebut(updatedPh.getHeureFin());
                ph.setJourFin(updatedPh.getJourFin());
                ph.setHeureFin(updatedPh.getHeureFin());
                ph.setPoste(updatedPh.getPoste());
                ph.setLieu(updatedPh.getLieu());
                ph.setNotes(updatedPh.getNotes());
                return plageHoraireRepository.save(ph);
            }).orElse(null);
    }

    float computeDuree(Long pHid){return -1f;}


    Map<TypeMajoration,Float> computeHeursMajorees(Long pHid){ return new HashMap<TypeMajoration,Float>();}


}
