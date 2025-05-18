package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.service.AuthentificationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ModifMonProfilController {
    @FXML private TextField nomModifProfilField;
    @FXML private TextField idModifProfilField;
    @FXML private TextField emailModifProfilField;
    @FXML private TextField mdpModifProfilFieldVisible;
    @FXML private TextField mdpModifProfilField;
    @FXML private CheckBox showPasswordCheckBoxModifProfil;
    @Autowired
    AuthentificationService authentificationService;


    private VoirMonProfilController voirMonProfilController;
    @FXML private Button retour;
    @FXML private Button validerButton;
    private sessionUtilisateur userSession;
    //private VoirMonProfilController voirMonProfilController;

    @FXML
    public void initialize() {
        /*
        nomModifProfilField.setText(user.getNom());
        emailModifProfilField.setText(user.getEmail());
        mdpModifProfilField.setText(user.getMotDePasse()); //à afficher avec précaution
        idModifProfilField.setText(String.valueOf(user.getId()));
        */
        mdpModifProfilFieldVisible.setVisible(false);
        mdpModifProfilFieldVisible.setManaged(false);
    }



    /*
    public void setUserSession(sessionUtilisateur userSession) {
        System.out.println(userSession);
        this.userSession = userSession;
        Utilisateur user = this.userSession.getUtilisateurConnecte();
        //Utilisateur user = use;

        if (user != null) {
            nomModifProfilField.setText(user.getNom());
            emailModifProfilField.setText(user.getEmail());
            mdpModifProfilField.setText(user.getMotDePasse()); //à afficher avec précaution
            idModifProfilField.setText(String.valueOf(user.getId()));
        }
    }

     */



    public void setUserSession(sessionUtilisateur userSession) {
        this.userSession = userSession;
        Utilisateur user = userSession.getUtilisateurConnecte();

        if (user != null) {
            nomModifProfilField.setText(user.getNom());
            emailModifProfilField.setText(user.getEmail());
            mdpModifProfilField.setText(user.getMotDePasse());
            idModifProfilField.setText(String.valueOf(user.getId()));
        }
    }


    @FXML
    private void toggleAfficherMotDePasse() {
        if (showPasswordCheckBoxModifProfil.isSelected()) {
            mdpModifProfilFieldVisible.setText(mdpModifProfilField.getText());
            mdpModifProfilFieldVisible.setEditable(true);
            mdpModifProfilFieldVisible.setVisible(true);
            mdpModifProfilFieldVisible.setManaged(true);
            mdpModifProfilField.setVisible(false);
            mdpModifProfilField.setEditable(true);
            mdpModifProfilField.setManaged(false);
        } else {
            mdpModifProfilField.setText(mdpModifProfilFieldVisible.getText());
            mdpModifProfilField.setVisible(true);
            mdpModifProfilFieldVisible.setEditable(true);
            mdpModifProfilField.setEditable(true);
            mdpModifProfilField.setManaged(true);
            mdpModifProfilFieldVisible.setVisible(false);
            mdpModifProfilFieldVisible.setManaged(false);
        }
    }

    @FXML
    private void enregistrerModifProfil() {
        if (userSession != null) {
            Utilisateur user = userSession.getUtilisateurConnecte();
            user.setNom(nomModifProfilField.getText());
            user.setEmail(emailModifProfilField.getText());

            String nouveauMotDePasse = showPasswordCheckBoxModifProfil.isSelected()
                    ? mdpModifProfilFieldVisible.getText()
                    : mdpModifProfilField.getText();
            user.setMotDePasse(nouveauMotDePasse);

            // ✅ Sauvegarde via le service
            authentificationService.mettreAJourUtilisateur(user);

            // Fermer la fenêtre
            Stage popupStage = (Stage) validerButton.getScene().getWindow();
            popupStage.close();
        }
    }



    public void setVoirMonProfilController(VoirMonProfilController controller) {
        this.voirMonProfilController = controller;
    }

    @FXML public void closePopup(ActionEvent event) throws IOException {
        Stage popupStage = (Stage) retour.getScene().getWindow();
        popupStage.close();

    }
}
