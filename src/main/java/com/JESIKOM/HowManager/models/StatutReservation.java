package com.JESIKOM.HowManager.models;

public enum StatutReservation {
    EN_ATTENTE("EN_ATTENTE"),
    CONFIRMEE("CONFIRMEE"),
    ANNULEE("ANNULEE"),
    EN_COURS("EN_COURS"),
    TERMINEE("TERMINEE");


    private final String label;

    StatutReservation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static StatutReservation labelToStatutReservation(String label) {
        for (StatutReservation s : values()) {
            if (s.getLabel().equalsIgnoreCase(label)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Statut de réservation inconnu : " + label);
    }


}
