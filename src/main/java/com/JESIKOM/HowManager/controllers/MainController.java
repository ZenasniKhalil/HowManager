package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.EvenementClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class MainController implements Initializable {

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
    @FXML private Button reservations;
    @FXML
    private TableView<EvenementClient> tableViewEvenements;

    @FXML private TableColumn<EvenementClient, String> colIdClient;
    @FXML private TableColumn<EvenementClient, String> colIdLogement;
    @FXML private TableColumn<EvenementClient, String> colNature;
    @FXML private TableColumn<EvenementClient, LocalDate> colDate;
    private ObservableList<EvenementClient> listeEvenements = FXCollections.observableArrayList();


    public MainController() {
        System.out.println("MainController instancié !");
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        //Rendre l'image cliquable pour rouvrir la popup
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        /*Diagramme circulaire logements occupés début*/
        chargerDiagrammeLogementsOccupes();
        /*Diagramme circulaire logements occupés fin*/

        /*Histogramme début*/
        chargerHistogrammeReservations(2025);
        /*Histogramme fin*/

        /*Diagramme circulaire logements disponibles début*/
        chargerDiagrammeLogementsDisponibles();
        /*Diagramme circulaire logements disponibles fin*/

        /*Table Check in / Check out debut*/
        // Liaison colonnes -> propriétés EvenementClient
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("identifiantClient"));
        colIdLogement.setCellValueFactory(new PropertyValueFactory<>("numeroLogement"));
        colNature.setCellValueFactory(new PropertyValueFactory<>("nature"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        chargerEvenementsDepuisBDD();
        tableViewEvenements.setItems(listeEvenements);
        /*Table Check in / Check out fin*/
    }

    private void chargerEvenementsDepuisBDD() {
        String url = "jdbc:h2:mem:testdb";
        String user = "demo";
        String password = "";

        /*
        String sql = "SELECT client_id, logement_id, nature, date_evenement FROM ( " +
                "SELECT r.client_id, r.numero_logement, 'Check-in' AS nature, r.date_check_in AS date_evenement " +
                "FROM Reservation r WHERE r.date_check_in >= CURRENT_DATE " +
                "UNION " +
                "SELECT r.client_id, r.numero_logement, 'Check-out' AS nature, r.date_check_out AS date_evenement " +
                "FROM Reservation r WHERE r.date_check_out >= CURRENT_DATE " +
                ") AS events ORDER BY date_evenement ASC";

         */
        String sql = "SELECT client_id, logement_id, nature, date_evenement FROM ( " +
                "SELECT r.client_id, r.logement_id, 'Check-in' AS nature, r.check_in AS date_evenement " +
                "FROM reservation r WHERE r.check_in >= CURRENT_DATE " +
                "UNION " +
                "SELECT r.client_id, r.logement_id, 'Check-out' AS nature, r.check_out AS date_evenement " +
                "FROM reservation r WHERE r.check_out >= CURRENT_DATE " +
                ") AS events ORDER BY date_evenement ASC";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            listeEvenements.clear();

            while (rs.next()) {
                String idClient = rs.getString("id_client");
                String numLogement = rs.getString("numero_logement");
                String nature = rs.getString("nature");
                LocalDate dateEvenement = rs.getDate("date_evenement").toLocalDate();

                listeEvenements.add(new EvenementClient(idClient, numLogement, nature, dateEvenement));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void chargerDiagrammeLogementsOccupes() {
        //Connexion à la base
        String url = "jdbc:h2:mem:testdb";
        String user = "demo";
        String password = "";

        Map<String, Integer> typeCounts = new HashMap<>();

        String query = "SELECT type, COUNT(*) AS total FROM Logement WHERE disponible = false GROUP BY type";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String type = rs.getString("type");
                int count = rs.getInt("total");
                typeCounts.put(type, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Vider les anciennes données du graphique
        pieChart_logements_occupes.getData().clear();

        // Ajouter les données récupérées
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart_logements_occupes.getData().add(slice);
        }
    }

    private void chargerDiagrammeLogementsDisponibles() {
        //Connexion à la base
        String url = "jdbc:h2:mem:testdb";
        String user = "demo";
        String password = "";

        Map<String, Integer> typeCounts = new HashMap<>();

        String query = "SELECT type, COUNT(*) AS total FROM Logement WHERE disponible = true GROUP BY type";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String type = rs.getString("type");
                int count = rs.getInt("total");
                typeCounts.put(type, count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Vider les anciennes données du graphique
        pieChart_logements_disponibles.getData().clear();

        // Ajouter les données récupérées
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart_logements_disponibles.getData().add(slice);
        }
    }

    private void chargerHistogrammeReservations(int annee) {
        // Vider les anciennes données
        barChart.getData().clear();

        // Créer une série pour le BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(String.valueOf(annee));

        // Préparer une carte mois → nombre de réservations (initialisée à 0)
        Map<Integer, Integer> reservationsParMois = new HashMap<>();
        for (int mois = 1; mois <= 12; mois++) {
            reservationsParMois.put(mois, 0);
        }

        // Connexion à la base
        String url = "jdbc:h2:mem:testdb"; // à adapter
        String user = "demo";
        String password = ""; // à adapter
        String query = "SELECT MONTH(date_reservation) AS mois, COUNT(*) AS total " +
                "FROM Reservation " +
                "WHERE YEAR(date_reservation) = ? " +
                "GROUP BY mois";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, annee);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int mois = rs.getInt("mois");
                int total = rs.getInt("total");
                reservationsParMois.put(mois, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter les données au BarChart
        String[] nomsMois = {
                "Jan", "Fév", "Mars", "Avr", "Mai", "Juin",
                "Juil", "Août", "Sep", "Oct", "Nov", "Déc"
        };

        for (int mois = 1; mois <= 12; mois++) {
            int total = reservationsParMois.get(mois);
            series.getData().add(new XYChart.Data<>(nomsMois[mois - 1], total));
        }

        barChart.getData().add(series);
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

    public void voirClients(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
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

}
