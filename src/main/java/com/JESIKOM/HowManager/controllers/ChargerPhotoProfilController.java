package com.JESIKOM.HowManager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
@Controller
public class ChargerPhotoProfilController {
    private MainController mainController;
    private EnregistrerClientController enregistrerClientController;
    private ListesClientsController listesClientsController;
    @FXML private Button btnNo;


    @FXML
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    public void setEnregistrerClientController(EnregistrerClientController enregistrerClientController) {
        this.enregistrerClientController = enregistrerClientController;
    }

    @FXML
    public void setListeClientsController(ListesClientsController controller){
        this.listesClientsController = controller;
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
