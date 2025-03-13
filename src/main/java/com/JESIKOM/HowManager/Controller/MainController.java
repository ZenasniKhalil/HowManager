package com.JESIKOM.HowManager.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public MainController() {
        System.out.println("🎯 MainController instancié !");
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

    @FXML
    private Button importerPhotoProfil; // Récupère le bouton

    /*
    @FXML
    public void initialize() {
        // Modifier le texte du bouton
        //importerPhotoProfil.setText("Clique ici !");

        // Ajouter une action au clic
        importerPhotoProfil.setOnAction(event -> {
            System.out.println("Bouton cliqué !");
        });
    }

     */


    @FXML
    private void chargerPhotoProfil(ActionEvent event) throws IOException {
        // Charger la nouvelle page
        Parent pageChargerPhotoProfil = FXMLLoader.load(getClass().getResource("chargerPhotoProfil.fxml"));

        // Récupérer la scène actuelle
        Scene scene = ((Node) event.getSource()).getScene();

        // Récupérer la fenêtre (stage)
        Stage stage = (Stage) scene.getWindow();

        // Changer la scène
        stage.setScene(new Scene(pageChargerPhotoProfil));
        stage.show();
    }



    /*
    @FXML
    private void chargerPhotoProfil(ActionEvent event) {
        try {
            // Charger la nouvelle page
            Parent pageChargerPhotoProfil = FXMLLoader.load(getClass().getResource("chargerPhotoProfil.fxml"));

            // Récupérer la scène actuelle
            Scene scene = ((Node) event.getSource()).getScene();

            // Récupérer la fenêtre (stage)
            Stage stage = (Stage) scene.getWindow();

            // Changer la scène
            stage.setScene(new Scene(pageChargerPhotoProfil));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Afficher l'erreur dans la console
        }
    }

     */

    /*
    @FXML
    private void chargerPhotoProfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chargerPhotoProfil.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Charger Photo Profil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */







}
