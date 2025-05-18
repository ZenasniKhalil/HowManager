package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Personnel;
import com.JESIKOM.HowManager.service.PersonnelService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListeEmployesController {

    @FXML private MenuItem menuItem1;
    @FXML private MenuItem menuItem2;
    @FXML private MenuItem menuItem3;
    @FXML private MenuItem voirMonProfilButton;
    @FXML Button profileButton;
    @FXML private ImageView profileImage;

    @Autowired
    private PersonnelService personnelService;

    @FXML
    private TableView<Personnel> tablePersonnel;

    @FXML
    private TableColumn<Personnel, Long> colID;

    @FXML
    private TableColumn<Personnel, String> colNom;

    @FXML
    private TableColumn<Personnel, String> colPrenom;

    @FXML
    private TableColumn<Personnel, String> colEmail;

    @FXML
    private TableColumn<Personnel, String> colTelephone;

    @FXML
    private TableColumn<Personnel, String> colPoste;

    @FXML
    private TextField searchField;

    private ObservableList<Personnel> personnelData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        colID.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getMatricule())
        );
        colNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colPrenom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPrenom()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colTelephone.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPhone()));
        colPoste.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPoste()));

        loadPersonnel();
    }

    private void loadPersonnel() {
        List<Personnel> personnel = personnelService.getAllPersonnel();
        personnelData.setAll(personnel);
        tablePersonnel.setItems(personnelData);
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

    public void voirMonProfil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/voirMonProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ouvrirPageCreerPersonnel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerEmploye.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) tablePersonnel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ouvrirPersonnel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Personnel.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) tablePersonnel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
