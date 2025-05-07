package com.JESIKOM.HowManager.models;
/*
public enum TypeIdentite {
    CIN, PASSEPORT, PERMIS_DE_CONDUIRE, CARTE_SEJOUR
}

 */


public enum TypeIdentite {
    CNI("CNI"),
    PASSEPORT("Passeport"),
    PERMIS_CONDUIRE("Permis de conduire"),
    CARTE_SEJOUR("Carte de séjour");

    private final String label;

    TypeIdentite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TypeIdentite labelToTypeIdentite(String label) {
        for (TypeIdentite t : values()) {
            if (t.getLabel().equalsIgnoreCase(label)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Type identite inconnu : " + label);
    }
}
