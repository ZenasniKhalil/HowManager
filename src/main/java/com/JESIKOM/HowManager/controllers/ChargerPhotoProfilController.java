package com.JESIKOM.HowManager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ChargerPhotoProfilController {
    private MainController mainController;
    @FXML
    private Button btnNo;

    @FXML
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null && mainController != null) {
            mainController.setProfileImage(file);
        }
        closePopup();
    }

    @FXML
    public void closePopup() {
        Stage popupStage = (Stage) btnNo.getScene().getWindow();
        popupStage.close();
    }


}
