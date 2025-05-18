package com.JESIKOM.HowManager.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class PaiementClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idClient;
    private int idLogement;
    private double prixLogement;
    private double sommeVersee;
    private LocalDate dateEcheance;

    public PaiementClient(int idClient, int idLogement, double prixLogement, double sommeVersee, LocalDate dateEcheance) {
        this.idClient = idClient;
        this.idLogement = idLogement;
        this.prixLogement = prixLogement;
        this.sommeVersee = sommeVersee;
        this.dateEcheance = dateEcheance;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdLogement() {
        return idLogement;
    }

    public double getPrixLogement() {
        return prixLogement;
    }

    public double getSommeVersee() {
        return sommeVersee;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }
}

