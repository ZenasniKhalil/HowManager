package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private final EnregistrerClientValiderController enregistrerClientValiderController;
    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private Button retourListeClients;
    @FXML private Button validerButton;
    @FXML private AnchorPane enregistrerClientPane;
    ListesClientsController listesClientsController;

    @FXML
    private MenuButton menuButtonTypeIdentite;

    @FXML private MenuItem menuItemCni;
    @FXML private MenuItem menuItemPasseport;
    @FXML private MenuItem menuItemCarteSejour;
    @FXML private MenuItem menuItemPermisConduire;



    private ClientService clientService;

    @FXML
    private TextField nomField, prenomField, telephoneField, emailField, adresseField, natField, numIdField;
    @FXML private TextArea remarqueArea;
    @FXML private DatePicker ddnField;
    @FXML private TextField typeIdField;

    public EnregistrerClientController(ClientService clientService, EnregistrerClientValiderController enregistrerClientValiderController){
        this.clientService=clientService;
        this.enregistrerClientValiderController = enregistrerClientValiderController;
    }

    @FXML
    private void ouvrirPopupConfirmation() {
        System.out.println("dans ouvrirPopConfirmation");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            // Récupérer le contrôleur de la popup
            EnregistrerClientValiderController popupController = loader.getController();
            System.out.println("controller loaded");

            // Lui passer les données du formulaire
            popupController.setClientInfos(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    adresseField.getText(),
                    ddnField.getValue(),
                    natField.getText(),
                    numIdField.getText(),
                    typeIdField.getText(),
                    remarqueArea.getText()
            );
            System.out.println("setclientInfo");

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
            EnregistrerClientValiderController valideur = fxmlLoader.getController();
            Client client = new Client(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    adresseField.getText(),
                    ddnField.getValue(),
                    natField.getText(),
                    numIdField.getText(),
                    TypeIdentite.labelToTypeIdentite(menuButtonTypeIdentite.getText()),
                    remarqueArea.getText()
            );
            valideur.setClient(client);

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
        menuItemCni.setOnAction(e -> menuButtonTypeIdentite.setText("CNI"));
        menuItemPasseport.setOnAction(e -> menuButtonTypeIdentite.setText("Passeport"));
        menuItemCarteSejour.setOnAction(e -> menuButtonTypeIdentite.setText("Carte de séjour"));
        menuItemPermisConduire.setOnAction(e -> menuItemPermisConduire.setText("Permis de conduire"));
    }

}
