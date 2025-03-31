package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.PlageHoraire;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.PlageHoraireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PlageHoraireService {
    @Autowired
    PlageHoraireRepository plageHoraireRepository;

    public PlageHoraire getPlageHoraireById(Long pHid){return null;}

    PlageHoraire addPlageHoraire(PlageHoraire pH) {return null;}

    void deletePlageHoraire(long pHid) {}

    PlageHoraire updatePlageHoraire(long pHid, PlageHoraire updatedPh) {return null;}

    float computeDuree(Long pHid){return -1f;}


    Map<TypeMajoration,Float> computeHeursMajorees(Long pHid){ return new HashMap<TypeMajoration,Float>();}
}
