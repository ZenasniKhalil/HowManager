package com.JESIKOM.HowManager.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class VoirMonProfilController {
    @FXML private ChoiceBox<String> typeContrat;
    @FXML private PasswordField passwordField;
    @FXML private TextField textField;
    @FXML private CheckBox showPasswordCheckBox;
    private MainController mainController;
    @FXML private Button retour;

    @FXML
    public void initialize() {
        // Ajouter les options à la ChoiceBox
        //typeContrat.setItems(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));

        // Définir une valeur par défaut (optionnel)
        //typeContrat.setValue("Option 1");



        // Synchronisation initiale
        textField.setText(passwordField.getText());

        // Gestion du changement de visibilité
        showPasswordCheckBox.setOnAction(event -> {
            if (showPasswordCheckBox.isSelected()) {
                textField.setText(passwordField.getText());
                textField.setVisible(true);
                passwordField.setVisible(false);
            } else {
                passwordField.setText(textField.getText());
                passwordField.setVisible(true);
                textField.setVisible(false);
            }
        });

        // Mise à jour en temps réel
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setText(newValue);
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue);
        });
    }

    @FXML
    public void closePopup() {
        Stage popupStage = (Stage) retour.getScene().getWindow();
        popupStage.close();
    }
}
