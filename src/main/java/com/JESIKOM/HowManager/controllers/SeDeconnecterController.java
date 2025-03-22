package com.JESIKOM.HowManager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SeDeconnecterController {

    private MainController mainController;
    @FXML private Button btnYes;
    @FXML private Button btnNo;

    @FXML
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    public void deconnexion(ActionEvent event) {
        //à finir
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seConnecterOuCreerCompte.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            //ChargerPhotoProfilController popupController = loader.getController();
            //popupController.setMainController(this);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void closePopup(){
        //à finir
        Stage popupStage = (Stage) btnNo.getScene().getWindow();
        popupStage.close();
    }
}
