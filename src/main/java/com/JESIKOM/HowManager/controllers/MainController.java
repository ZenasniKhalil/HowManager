package com.JESIKOM.HowManager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    public MainController() {
        System.out.println("MainController instancié !");
    }

    @FXML
    private Label welcomeText;

    @FXML
    void onHelloButtonClick(ActionEvent event) {

    }

    @FXML
    void printHelloWorld(MouseEvent event) {
        System.out.println("Hello World");
    }

    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;

    /*
    @FXML
    public void initialize() {
        // Rendre l'image cliquable pour rouvrir la popup
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        // Optionnel : Rendre aussi le bouton cliquable
        //profileButton.setOnAction(event -> openPopup());
    }

     */

    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            ChargerPhotoProfilController popupController = loader.getController();
            popupController.setMainController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileImage(File file) {
        Image image = new Image(file.toURI().toString());
        profileImage.setImage(image);
        profileButton.setVisible(false); // Cache le bouton

        //Créer un cercle de la taille de l'ImageView
        double radius = Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2;
        Circle clip = new Circle(radius);
        clip.setCenterX(profileImage.getFitWidth() / 2);
        clip.setCenterY(profileImage.getFitHeight() / 2);

        //Appliquer le clip pour rendre l'image ronde
        profileImage.setClip(clip);
    }

}
