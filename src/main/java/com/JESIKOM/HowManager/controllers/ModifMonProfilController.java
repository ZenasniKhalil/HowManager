package com.JESIKOM.HowManager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ModifMonProfilController {
    @FXML private MenuButton menuButtonContrat;
    @FXML private MenuItem cdiItem, cddItem, caItem, cpItem, interimItem;
    @FXML private VoirMonProfilController voirMonProfilController;
    @FXML private Button retour;

    @FXML
    public void initialize() {

        cdiItem.setOnAction(event -> menuButtonContrat.setText("CDI"));
        cddItem.setOnAction(event -> menuButtonContrat.setText("CDD"));
        caItem.setOnAction(event -> menuButtonContrat.setText("Contrat d'apprentissage"));
        cpItem.setOnAction(event -> menuButtonContrat.setText("Contrat de professionnalisation"));
        interimItem.setOnAction(event -> menuButtonContrat.setText("Intérim"));
    }

    public void setMainController(VoirMonProfilController controller) {
        this.voirMonProfilController = controller;
    }

    @FXML public void closePopup(ActionEvent event) throws IOException {

        Stage popupStage = (Stage) retour.getScene().getWindow();
        popupStage.close();

    }
}
