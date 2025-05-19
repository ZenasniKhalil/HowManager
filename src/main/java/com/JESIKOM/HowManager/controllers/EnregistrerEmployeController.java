package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.service.PersonnelService;
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
import java.time.LocalDate;

@Component
public class EnregistrerEmployeController {

    @FXML private AnchorPane enregistrerPersonnelPane;
    @FXML private Button validerButton,annulerButton;
    @FXML private TextField nomField, prenomField, emailField, telephoneField, posteField, adresseField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private TextField lienContratField;
    @FXML private TextField tauxHoraireField;
    @FXML private TextField statusField;
    @FXML private TextField nationaliteField;

    @FXML private TextField nbHeureMoisField;
    @FXML private TextField nbHeureSemaineField;
    @FXML private TextField genreField;

    @FXML private Button retourListePersonnelButton;
    @FXML private Button profileButton;
    @FXML private ImageView profileImage;

    @Autowired
    private PersonnelService personnelService;

   // @FXML
    /*private void ouvrirConfirmationValider() throws IOException {
        try {
            BoxBlur blur = new BoxBlur(5, 5, 3);
            enregistrerPersonnelPane.setEffect(blur);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerPersonnelValider.fxml"));
            fxmlLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent popupRoot = fxmlLoader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(enregistrerPersonnelPane.getScene().getWindow());
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setOnHiding(e -> enregistrerPersonnelPane.setEffect(null));
            popupStage.setResizable(false);
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    public void validerEnregistrement() {
        Personnel p = new Personnel();
        p.setNom(nomField.getText());
        p.setPrenom(prenomField.getText());
        p.setEmail(emailField.getText());
        p.setPhone(telephoneField.getText());
        p.setPoste(posteField.getText());
        p.setAdresse(adresseField.getText());
        p.setLienContrat(lienContratField.getText());
        p.setTauxHoraire(Float.parseFloat(tauxHoraireField.getText()));
        p.setStatus(statusField.getText());
        p.setNbHeureMois(Integer.parseInt(nbHeureMoisField.getText()));
        p.setNationalite(nationaliteField.getText());
        p.setNbHeureSemaine(Integer.parseInt(nbHeureSemaineField.getText()));
        p.setGenre(genreField.getText());
        if (dateNaissancePicker.getValue() != null) {
            p.setDateNaissance(dateNaissancePicker.getValue());
        }
        personnelService.addPersonnel(p);



    }

    @FXML
    public void annulerEnregistrement() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ouvrirPersonnel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Personnel.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void retourTableauBord(ActionEvent event){
        try {
            System.out.println("retourTableauBord()");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Initialisation de EnregistrerPersonnelController avec AnchorPane : " + enregistrerPersonnelPane);
    }
}
