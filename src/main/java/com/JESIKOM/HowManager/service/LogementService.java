package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.TypeLogement;
import com.JESIKOM.HowManager.repository.LogementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogementService implements ILogementService {
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


    public List<Logement> getLogementByCapacite(int capacite) {
        return logementRepository.findLogementByCapacite(capacite);
    }


    @Override
    public List<Logement> getLogementDisponibleByCapacite(int capacite) {
        return logementRepository.findLogementByCapaciteAndDisponibleTrue(capacite);
    }

    @Override
    public List<Logement> getLogementByPrix(double prix) {
        return logementRepository.findLogementByPrixOrderByPrixAsc(prix);
    }

    @Override
    public List<Logement> getLogementDisponibleByPrix(double prix) {
        return logementRepository.findLogementByPrixAndDisponibleTrueOrderByPrixAsc(prix);
    }


    public List<Logement> getAllLogementByDisponible(boolean disponible) {
        return logementRepository.findLogementByDisponible(disponible);
    }


    @Override
    public List<Logement> getLogementByType(TypeLogement type) {
        return logementRepository.findLogementByType(type);
    }
}
