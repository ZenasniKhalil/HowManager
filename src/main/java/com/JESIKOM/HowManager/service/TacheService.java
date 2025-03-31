package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Tache;
import com.JESIKOM.HowManager.models.TypeMajoration;
import com.JESIKOM.HowManager.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TacheService {

    @Autowired
    TacheRepository tacheRepository;

    public Tache getTacheById(Long tid){return null;}

    public Tache addTache(Tache t) {return null;}

    public void deleteTache(long tid) {}


    public float computeDuree(Long tid){return -1f;}


    Map<TypeMajoration,Float> computeHeuresMajorees(Long tid){
        return new HashMap<TypeMajoration,Float>();
    }
}



