package com.JESIKOM.HowManager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnregistrerClientController {
    @FXML
    Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirMonProfilButton;
    ListesClientsController listesClientsController;

    public void chargerPhotoProfil(ActionEvent actionEvent) {
    }

    public void voirMonProfil(ActionEvent actionEvent) {
    }

    public void setListeClientController(ListesClientsController controller){
        this.listesClientsController = controller;
    }
}
