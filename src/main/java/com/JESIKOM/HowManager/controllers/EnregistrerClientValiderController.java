package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.models.TypeIdentite;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.LogementService;
import com.JESIKOM.HowManager.service.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.Buffer;
import java.time.LocalDate;

@Component
@Setter
@Getter
public class EnregistrerClientValiderController {
    @FXML Button btnNo;
    @FXML Button btnYes;


    private ClientService clientService;
    private ReservationService reservationService;
    private LogementService logementService;
    private LocalDate ddn;
    private Client client;
    private Reservation reservation;
    private Logement logement;


    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;
    @FXML private TextArea remarqueArea;



    //Injection Spring
    public EnregistrerClientValiderController(ClientService clientService, ReservationService reservationService){
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    public void closepopup(){
        Stage popupStage = (Stage) btnNo.getScene().getWindow();
        popupStage.close();
    }

    @FXML
    private void enregistrerClient(ActionEvent event) {

        try {
            System.out.println(" affichage" +client.toString()+"\n");
            Client savedClient = clientService.addClient(client);
            System.out.println(" affichage" +reservation.toString()+"\n");
            Reservation savedReservation = reservationService.addReservation(reservation);

            if (savedClient != null && savedReservation != null) {
                System.out.println("Test id Client : "+savedClient.getId());
                System.out.println("Test id reservation : "+savedReservation.getId());
                ouvrirConfirmationEnregistrementClient(event);
            } else {
                System.out.println("client/réservation null");
                showAlert("Erreur", "Ce client/cette réservation existe déjà ou des champs sont invalides.");
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


    public void setClientInfos(String nom, String prenom, String telephone,
                               String email, String adresse, LocalDate ddn,
                               String nat, String numId, String typeId,
                               String remarque) {
        //this.nom = nom;
        this.client.setNom(nom);
        //this.prenom = prenom;
        this.client.setPrenom(prenom);
        this.client.setTelephone(telephone);
        this.client.setEmail(email);
        this.client.setAdresse(adresse);
        this.client.setDateNaissance(ddn);
        this.client.setNationalite(nat);
        this.client.setNumeroIdentite(numId);
        this.client.setTypeIdentite(TypeIdentite.labelToTypeIdentite(typeId));
        this.client.setRemarque(remarque);
        System.out.println(client.toString());
    }

    @FXML
    private void onNonClicked(ActionEvent event) {
        Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.close(); // juste fermer le popup
    }

}
