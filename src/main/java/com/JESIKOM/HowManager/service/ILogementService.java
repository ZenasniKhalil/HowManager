package com.JESIKOM.HowManager.service;

import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.TypeLogement;

import java.util.List;
import java.util.Optional;

interface ILogementService {
    List<Logement> getAllLogements();

    Optional<Logement> getLogementById(int numero);

    Logement addLogement(Logement logement);

    Logement updateLogement(int numero, Logement updatedLogement);

    void deleteLogement(int numero);

    List<Logement> getLogementByCapacite(int capacite);

    List<Logement> getLogementDisponibleByCapacite(int capacite);

    List<Logement> getLogementByPrix(Double prix);

    List<Logement> getLogementDisponibleByPrix(Double prix);

    List<Logement> getAllLogementByDisponible(boolean disponible);

    List<Logement> getLogementByType(TypeLogement type);

    int getNbLogementOccupeByType(TypeLogement type);

    int getNbLogementDisponibleByType(TypeLogement type);

    int getNbLogementDisponible();

    int getNbLogementOccupe();








}