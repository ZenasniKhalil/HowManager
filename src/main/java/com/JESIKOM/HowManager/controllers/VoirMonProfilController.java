package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class VoirMonProfilController {
    //@Autowired
    private sessionUtilisateur userSession;
    @FXML private TextField nomProfilField;
    @FXML private PasswordField mdpProfilField;
    @FXML private TextField mdpProfilFieldVisible;
    @FXML private TextField IdProfilField;
    @FXML private TextField emailProfilField;
    @FXML private CheckBox showPasswordCheckBoxProfil;
    private MainController mainController;
    @FXML private Button retourButton;
    @FXML Button modifProfilButton;
    //@Autowired private  Utilisateur use;

    public void setUserSession(sessionUtilisateur userSession) {
        System.out.println(userSession);
        this.userSession = userSession;
        Utilisateur user = this.userSession.getUtilisateurConnecte();
        //Utilisateur user = use;

        if (user != null) {
            nomProfilField.setText(user.getNom());
            emailProfilField.setText(user.getEmail());
            mdpProfilField.setText(user.getMotDePasse()); //à afficher avec précaution
            IdProfilField.setText(String.valueOf(user.getId()));
        }
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void closePopup() {
        Stage popupStage = (Stage) retourButton.getScene().getWindow();
        popupStage.close();
    }

/*
    public void modifierProfil(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modifMonProfil.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);


            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            popupStage.showAndWait();
    }

 */

    public void modifierProfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modifMonProfil.fxml"));
        loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
        Parent root = loader.load();

        ModifMonProfilController modifController = loader.getController();
        modifController.setUserSession(userSession); // 🔁 <-- on transmet la session

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(root));
        popupStage.setResizable(false);

        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        popupStage.showAndWait();
    }


    @FXML
    private void toggleAfficherMotDePasse() {
        if (showPasswordCheckBoxProfil.isSelected()) {
            mdpProfilFieldVisible.setText(mdpProfilField.getText());
            mdpProfilFieldVisible.setVisible(true);
            mdpProfilFieldVisible.setManaged(true);
            mdpProfilField.setVisible(false);
            mdpProfilField.setManaged(false);
        } else {
            mdpProfilField.setText(mdpProfilFieldVisible.getText());
            mdpProfilField.setVisible(true);
            mdpProfilField.setManaged(true);
            mdpProfilFieldVisible.setVisible(false);
            mdpProfilFieldVisible.setManaged(false);
        }
    }

}
