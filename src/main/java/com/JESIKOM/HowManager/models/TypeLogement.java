package com.JESIKOM.HowManager.models;


public enum TypeLogement {
    APPARTEMENT("APPARTEMENT"),
    MAISON("MAISON"),
    STUDIO("STUDIO"),
    BUNGALOW("BUNGALOW");

    private final String label;

    TypeLogement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TypeLogement labelToTypeLogement(String label) {
        for (TypeLogement t : values()) {
            if (t.getLabel().equalsIgnoreCase(label)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Mode de paiement inconnu : " + label);
    }
}
