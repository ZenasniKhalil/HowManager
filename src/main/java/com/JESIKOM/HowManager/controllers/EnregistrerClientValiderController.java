package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.Buffer;

@Component
public class EnregistrerClientValiderController {
    @FXML Button btnNo;
    @FXML Button btnYes;


    private ClientService clientService;

    private Client client;

    /*
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;
    @FXML private TextArea remarqueArea;

     */

    private String nom, prenom, telephone, email, remarque;

    //Injection Spring
    public EnregistrerClientValiderController(ClientService clientService){
        this.clientService = clientService;
    }

    public void closepopup(){
        Stage popupStage = (Stage) btnNo.getScene().getWindow();
        popupStage.close();
    }

    @FXML
    private void enregistrerClient(ActionEvent event) {
        try {
            Client nouveauClient = new Client();

            Client savedClient = clientService.addClient(nouveauClient);

            if (savedClient != null) {
                System.out.println("Test id Client : "+savedClient.getId());
                ouvrirConfirmationEnregistrementClient(event);
            } else {
                System.out.println("client null");
                showAlert("Erreur", "Ce client existe déjà ou des champs sont invalides.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’enregistrer le client : " + e.getMessage());
        }
    }

    public void ouvrirConfirmationEnregistrementClient(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValiderOui.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            //ChargerPhotoProfilController popupController = loader.getController();
            //popupController.setMainController(this);


            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();


            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setClientInfos(String nom,String prenom,String telephone,String email,String remarque,ClientService clientService) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.remarque = remarque;
        this.clientService = clientService;
    }

}
