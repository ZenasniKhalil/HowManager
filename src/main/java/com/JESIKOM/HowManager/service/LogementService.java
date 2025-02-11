package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.repository.LogementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogementService {
    @Autowired
    private LogementRepository logementRepository;

    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }

    public Optional<Logement> getLogementById(int numero) {
        return logementRepository.findById(numero);
    }

    public Logement addLogement(Logement logement) {
        return logementRepository.save(logement);
    }

    public Logement updateLogement(int numero, Logement updatedLogement) {
        return logementRepository.findById(numero).map(logement -> {
            logement.setType(updatedLogement.getType());
            logement.setCapacite(updatedLogement.getCapacite());
            logement.setDisponible(updatedLogement.isDisponible());
            logement.setPropre(updatedLogement.isPropre());
            logement.setCommentaire(updatedLogement.getCommentaire());
            logement.setPrix(updatedLogement.getPrix());
            return logementRepository.save(logement);
        }).orElse(null);
    }

    public void deleteLogement(int numero) {
        logementRepository.deleteById(numero);
    }
}
