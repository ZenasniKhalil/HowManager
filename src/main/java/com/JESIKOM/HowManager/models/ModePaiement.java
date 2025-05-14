package com.JESIKOM.HowManager.models;

public enum ModePaiement {
    ESPECE("ESPECE"),
    CARTE_BANCAIRE("CARTE_BANCAIRE"),
    MOBILE_MONEY("MOBILE_MONEY"),
    VIREMENT("VIREMENT");

    private final String label;

    ModePaiement(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ModePaiement labelToModePaiement(String label) {
        for (ModePaiement m : values()) {
            if (m.getLabel().equalsIgnoreCase(label)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Mode de paiement inconnu : " + label);
    }
}
