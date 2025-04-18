package com.JESIKOM.HowManager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirProfil;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private MenuItem deconnexionItem;
    @FXML private PieChart pieChart_logements_occupes;
    @FXML private PieChart pieChart_logements_disponibles;
    @FXML private BarChart<String, Number> barChart;
    @FXML private Label yearLabel;
    private int currentYear = 2025; // Année affichée par défaut
    private final Map<Integer, int[]> reservationsData = new HashMap<>();

    public MainController() {
        System.out.println("MainController instancié !");
    }

    @FXML
    public void initialize() {
        //Rendre l'image cliquable pour rouvrir la popup
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        /*Diagramme circulaire logements occupés début*/
        PieChart.Data slice1_o = new PieChart.Data("Suite", 30);
        PieChart.Data slice2_o = new PieChart.Data("Double", 25);
        PieChart.Data slice3_o = new PieChart.Data("Simple", 45);
        pieChart_logements_occupes.getData().addAll(slice1_o, slice2_o, slice3_o);
        /*Diagramme circulaire logements occupés fin*/

        /*Histogramme début*/
        // Initialiser les données pour chaque année
        reservationsData.put(2024, new int[]{100, 90, 95, 110, 130, 120, 150, 140, 130, 120, 110, 100});
        reservationsData.put(2025, new int[]{120, 85, 95, 130, 150, 180, 170, 160, 150, 140, 130, 120});
        reservationsData.put(2026, new int[]{130, 95, 105, 140, 160, 190, 180, 170, 160, 150, 140, 130});
        loadData(currentYear); // Charger les données de l'année par défaut
        /*Histogramme fin*/

        /*Diagramme circulaire logements disponibles début*/
        PieChart.Data slice1_d = new PieChart.Data("Suite", 20);
        PieChart.Data slice2_d = new PieChart.Data("Double", 50);
        PieChart.Data slice3_d = new PieChart.Data("Simple", 30);
        pieChart_logements_disponibles.getData().addAll(slice1_d, slice2_d, slice3_d);
        /*Diagramme circulaire logements disponibles fin*/

    }

    private void loadData(int year) {
        barChart.getData().clear(); // Effacer les anciennes données

        int[] reservations = reservationsData.get(year);
        if (reservations == null) return; // Si aucune donnée pour cette année, on arrête

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Réservations " + year);

        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<>(months[i], reservations[i]));
        }

        barChart.getData().add(series);
        yearLabel.setText(String.valueOf(year)); // Met à jour l'affichage de l'année
    }

    @FXML
    private void previousYear() {
        currentYear--;
        loadData(currentYear);
    }

    @FXML
    private void nextYear() {
        currentYear++;
        loadData(currentYear);
    }

    @FXML
    public void changeBackgroundButtonColor(ActionEvent event) {
        /*
        Button clickedButton = (Button) event.getSource(); // Récupère le bouton qui a été cliqué
        clickedButton.setStyle("-fx-background-color: #3498db;"); // Change sa couleur de fond

         */
    }


    /*
    private void loadData(int[] reservations, String year) {
        barChart.getData().clear(); // Effacer les anciennes données

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Réservations " + year);

        String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

        for (int i = 0; i < months.length; i++) {
            series.getData().add(new XYChart.Data<>(months[i], reservations[i]));
        }

        barChart.getData().add(series); // Ajouter la série au graphique
    }



    @FXML
    private void loadData2024() {
        int[] reservations2024 = {100, 90, 95, 110, 130, 120, 150, 140, 130, 120, 110, 100};
        loadData(reservations2024, "2024");
    }

    @FXML
    private void loadData2025() {
        int[] reservations2025 = {120, 85, 95, 130, 150, 180, 170, 160, 150, 140, 130, 120};
        loadData(reservations2025, "2025");
    }

    @FXML
    private void loadData2026() {
        int[] reservations2026 = {130, 95, 105, 140, 160, 190, 180, 170, 160, 150, 140, 130};
        loadData(reservations2026, "2026");
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

    public void voirMonProfil(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/voirMonProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            //ChargerPhotoProfilController popupController = loader.getController();
            //popupController.setMainController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void demandeDeconnexion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seDeconnecter.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            SeDeconnecterController popupController = loader.getController();
            popupController.setMainController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
