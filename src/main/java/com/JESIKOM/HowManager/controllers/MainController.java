package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.service.LogementService;
import com.JESIKOM.HowManager.service.ReservationService;
import com.JESIKOM.HowManager.service.TacheService;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class MainController implements Initializable {

    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuButton nomUtilisateur;
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
    @FXML private Button tableau__bord;
    @FXML private Rectangle tableau_bord;


    private Utilisateur utilisateur;
    /*Début tâches diverses*/
    @FXML private TableView<Tache> tableTachesDiverses;
    @FXML private TableColumn<Tache, String> colDescriptionTache;
    @FXML private TableColumn<Tache, LocalDate> colDateEcheanceTache;
    @FXML private TableColumn<Tache, String> colStatutTache;
    private ObservableList<Tache> tacheData = FXCollections.observableArrayList();
    @Autowired
    private TacheService tacheService;
    /*Fin tâches diverses*/

    /*Début paiement*/
    @FXML private TableView<PaiementClient> tablePaiement;
    @FXML private TableView<PaiementClient>tableViewEvenements;
    @FXML private TableColumn<PaiementClient, Long>colIDClientPaiement;
    @FXML private TableColumn<PaiementClient, Integer>colIDLogementPaiement;
    @FXML private TableColumn<PaiementClient, Double>colPrixLogementPaiement;
    @FXML private TableColumn<PaiementClient, Double>colSommeVerseePaiement;
    @FXML private TableColumn<PaiementClient, LocalDate>colDateEcheancePaiement;
    private ObservableList<PaiementClient> paiementData = FXCollections.observableArrayList();
    /*Fin paiement*/

    @Autowired
    private sessionUtilisateur userSession;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LogementService logementService;


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


        /*Début TachesDiverves*/
        //Associer les propriétés de Client aux colonnes
        colDescriptionTache.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDateEcheanceTache.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colStatutTache.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadTaches();
        /*Fin TachesDiverves*/

        /*Début PaiementClient*/
        colIDClientPaiement.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIDLogementPaiement.setCellValueFactory(new PropertyValueFactory<>("idLogement"));
        colPrixLogementPaiement.setCellValueFactory(new PropertyValueFactory<>("prixLogement"));
        colSommeVerseePaiement.setCellValueFactory(new PropertyValueFactory<>("sommeVersee"));
        colDateEcheancePaiement.setCellValueFactory(new PropertyValueFactory<>("dateEcheance"));
        tablePaiement.setItems(paiementData);
        loadPaiementDataFromDB();
        /*Fin PaiementClient*/
    }

    private void loadPaiementDataFromDB() {
        paiementData.clear();
        List<Reservation> reservationList = reservationService.getAllReservations();
        for (Reservation r : reservationList){
            Long clientId = r.getClient().getId();
            int logementId = r.getLogement().getNumero();
            double prixLogement = r.getLogement().getPrix();
            double sommeVersee = r.getAcompte();
            LocalDate dateEcheance = reservationService.getDate_Fin(r);
            if(reservationService.isPaidReservation(r)) {
                PaiementClient paiement = new PaiementClient(clientId, logementId, prixLogement, sommeVersee, dateEcheance);
                paiementData.add(paiement);
            }
        }
/*
        //Connexion à la base
        String url = "jdbc:h2:mem:testdb";
        String user = "demo";
        String password = "";

        String sql = """
        SELECT r.client_id, r.logement_id, l.prix AS prix_logement, r.acompte, r.check_out
        FROM reservation r
        JOIN logement l ON r.logement_id = l.logement_id
        WHERE r.acompte < l.prix
        """;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                int logementId = rs.getInt("logement_id");
                double prixLogement = rs.getDouble("prix_logement");
                double sommeVersee = rs.getDouble("acompte");
                LocalDate dateEcheance = rs.getDate("check_out").toLocalDate();

                PaiementClient paiement = new PaiementClient(clientId, logementId, prixLogement, sommeVersee, dateEcheance);
                paiementData.add(paiement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }


    private void loadTaches() {
        List<Tache> taches = tacheService.getAllTaches();
        tacheData.setAll(taches);
        tableTachesDiverses.setItems(tacheData);
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (nomUtilisateur != null && utilisateur != null) {
            nomUtilisateur.setText(utilisateur.getNom());
        }
    }




    private void chargerDiagrammeLogementsOccupes() {
       /* //Connexion à la base
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
        */
        //Récupération des données
        Map<String, Integer> typeCounts = new HashMap<>();

        for (TypeLogement t : TypeLogement.values()){
            System.out.println("type :"+t.toString());
            int count = logementService.getNbLogementOccupeByType(t);
            System.out.println("count : "+count);
            typeCounts.put(t.toString(),count);
        }

        //Vider les anciennes données du graphique
        pieChart_logements_occupes.getData().clear();

        //Ajouter les données récupérées
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart_logements_occupes.getData().add(slice);
        }
    }

    private void chargerDiagrammeLogementsDisponibles() {
        /*
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
        }*/
        //Récupération des données
        Map<String, Integer> typeCounts = new HashMap<>();

        for (TypeLogement t : TypeLogement.values()){
            System.out.println("type :"+t.toString());
            int count = logementService.getNbLogementDisponibleByType(t);
            typeCounts.put(t.toString(),count);
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

        for (int i=1; i<=12;i++){
            int count =reservationService.getCountReservationsByMoisandAnnee(i,annee);
            reservationsParMois.put(i,count);
        }
        /*
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
        }*/

        // Ajouter les données au BarChart
        String[] nomsMois = {
                "Jan", "Fév", "Mars", "Avr", "Mai", "Juin",
                "Juil", "Août", "Sep", "Oct", "Nov", "Déc"
        };

        for (int mois = 1; mois <= 12; mois++) {
            int total = reservationsParMois.get(mois);
            series.getData().add(new XYChart.Data<>(nomsMois[mois-1], total));
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
            //loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            VoirMonProfilController controller1 = loader.getController();
            controller1.setUserSession(this.userSession); // injecte ici

            //ModifMonProfilController controller2 = loader.getController();
            //controller2.setUserSession(this.userSession);


            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setUserSession(sessionUtilisateur session) {
        this.userSession = session;
    }

    public void demandeDeconnexion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seDeconnecter.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
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

    public void voirPersonnel(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Personnel.fxml"));
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

    public void voirStock(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Stock.fxml"));
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
