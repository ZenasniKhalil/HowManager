package com.JESIKOM.HowManager.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class VoirMonProfilController {
    @FXML
    private ChoiceBox<String> typeContrat;

    @FXML
    public void initialize() {
        // Ajouter les options à la ChoiceBox
        typeContrat.setItems(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));

        // Définir une valeur par défaut (optionnel)
        typeContrat.setValue("Option 1");
    }
}
