package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
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

    public void ouvrirConfirmationValider() throws IOException {
        try {

            // Appliquer un flou sur la fenêtre principale
            BoxBlur blur = new BoxBlur(5, 5, 3);
            enregistrerClientPane.setEffect(blur);


            // Charger la popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            fxmlLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
            Parent popupRoot = fxmlLoader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL); // bloque interaction avec la fenêtre principale
            popupStage.initOwner(enregistrerClientPane.getScene().getWindow()); // rattacher au parent
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setOnHiding(e -> {
                // Supprimer le flou lorsque la popup se ferme
                enregistrerClientPane.setEffect(null);
            });
            popupStage.setResizable(false);

            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("enregisterClientPane = " + enregistrerClientPane);
    }

}
