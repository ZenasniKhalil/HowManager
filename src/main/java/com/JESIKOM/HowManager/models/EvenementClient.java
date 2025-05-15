package com.JESIKOM.HowManager.models;
import java.time.LocalDate;

public class EvenementClient {
    private String identifiantClient;
    private String numeroLogement;
    private String nature;
    private LocalDate dateEvenement;

    public EvenementClient(String identifiantClient, String numeroLogement, String nature, LocalDate dateEvenement) {
        this.identifiantClient = identifiantClient;
        this.numeroLogement = numeroLogement;
        this.nature = nature;
        this.dateEvenement = dateEvenement;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public String getNumeroLogement() {
        return numeroLogement;
    }

    public String getNature() {
        return nature;
    }

    public LocalDate getDateEvenement() {
        return dateEvenement;
    }
}

