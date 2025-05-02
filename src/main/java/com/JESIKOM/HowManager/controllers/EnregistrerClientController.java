package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EnregistrerClientController {
    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private Button retourListeClients;
    @FXML private Button validerButton;
    @FXML private AnchorPane enregistrerClientPane;
    ListesClientsController listesClientsController;

    @Autowired
    private ClientService clientService;

    @FXML
    private TextField nomField, prenomField, telephoneField, emailField;
    @FXML
    private TextArea remarqueArea;

    @FXML
    private void ouvrirPopupConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            // Récupérer le contrôleur de la popup
            EnregistrerClientValiderController popupController = loader.getController();

            // Lui passer les données du formulaire
            popupController.setClientInfos(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    remarqueArea.getText(),
                    this.clientService
            );

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            ChargerPhotoProfilController popupController = loader.getController();
            popupController.setEnregistrerClientController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void chargerPhotoProfil(ActionEvent actionEvent) {
    }*/

    public void voirMonProfil(ActionEvent actionEvent) {
    }

    public void setListeClientController(ListesClientsController controller){
        this.listesClientsController = controller;
    }

    public void ouvrirListeClients(){
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) tableClients.getScene().getWindow(); // ← adapte ici aussi

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }

    @FXML
    public void ouvrirConfirmationValider() {
        try {
            // Appliquer un flou sur la fenêtre principale
            BoxBlur blur = new BoxBlur(5, 5, 3);
            enregistrerClientPane.setEffect(blur);

            // Charger la popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            Parent popupRoot = fxmlLoader.load();

            // Récupérer le contrôleur de la popup
            EnregistrerClientValiderController popupController = fxmlLoader.getController();

            // Injecter les données du formulaire
            popupController.setClientInfos(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    remarqueArea.getText(),
                    this.clientService
            );

            // Créer et afficher la popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(enregistrerClientPane.getScene().getWindow());
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setResizable(false);

            popupStage.setOnHiding(e -> {
                // Retirer le flou une fois la popup fermée
                enregistrerClientPane.setEffect(null);
            });

            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        System.out.println("enregisterClientPane = " + enregistrerClientPane);
    }

}
