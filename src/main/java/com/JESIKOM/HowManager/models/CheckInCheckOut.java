package com.JESIKOM.HowManager.models;
import java.time.LocalDate;

public class CheckInCheckOut {
    private String identifiantClient;
    private String numeroLogement;
    private String nature;
    private LocalDate dateCheck;

    public CheckInCheckOut(String identifiantClient, String numeroLogement, String nature, LocalDate dateCheck) {
        this.identifiantClient = identifiantClient;
        this.numeroLogement = numeroLogement;
        this.nature = nature;
        this.dateCheck = dateCheck;
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

    public LocalDate getDateCheck() {
        return dateCheck;
    }
}

