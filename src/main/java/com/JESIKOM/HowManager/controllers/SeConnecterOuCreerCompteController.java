package com.JESIKOM.HowManager.controllers;


import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.service.AuthentificationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeConnecterOuCreerCompteController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    //@FXML
    //private Label erreurLabel;

    @Autowired
    private AuthentificationService authService;

    @FXML
    public void handleConnexion() {
        String email = emailField.getText();
        String motDePasse = passwordField.getText();

        try {
            Utilisateur utilisateur = authService.authentifier(email, motDePasse);
            //erreurLabel.setText("");

            // Naviguer vers une autre vue ou stocker utilisateur
            System.out.println("Bienvenue " + utilisateur.getNom());

        } catch (Exception e) {
            //erreurLabel.setText("Connexion échouée : " + e.getMessage());
            System.out.println("Connexion échouée : " + e.getMessage());
        }
    }
}
