package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Logement;
import com.JESIKOM.HowManager.models.Reservation;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.LogementService;
import com.JESIKOM.HowManager.service.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListesClientsController {
    @FXML private MenuItem menuItem1;
    @FXML private MenuItem menuItem2;
    @FXML private MenuItem menuItem3;
    @FXML private MenuItem voirMonProfilButton;
    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;


    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, Long> colID;

    @FXML
    private TableColumn<Client, String> colNom;

    @FXML
    private TableColumn<Client, String> colPrenom;

    @FXML
    private TableColumn<Client, String> colEmail;

    @FXML
    private TableColumn<Client, String> colTelephone;

    @FXML
    private TableColumn<Client, String> colRemarque;

    @FXML
    private TextField searchField;

    private ObservableList<Client> clientsData = FXCollections.observableArrayList();



    @FXML
    public void initialize() {
        //Rendre l'image cliquable pour rouvrir la popup
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        //Associer les propriétés de Client aux colonnes
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colPrenom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPrenom()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colTelephone.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelephone()));
        colRemarque.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRemarque()));
        loadClients();

        // Gestion du clic
        tableClients.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double clic
                Client clientSelectionne = tableClients.getSelectionModel().getSelectedItem();
                if (clientSelectionne != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails du client");
                    alert.setHeaderText(null);//On va créer nous-mêmes l'en-tête

                    //Boutons personnalisés
                    ButtonType modifierBtn = new ButtonType("Modifier Client");
                    ButtonType fermerBtn = new ButtonType("Fermer", ButtonBar.ButtonData.CANCEL_CLOSE);

                    /*Gestion du modifierBtn
                    // Gestion du bouton cliqué
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == modifierBtn) {
                        //Ici, tu ouvres une fenêtre de modification par exemple :
                        ouvrirFenetreModification(client);
                    }
                    */


                    VBox content = new VBox(10);
                    content.setPadding(new Insets(10));

                    //"Titre 1" : Informations personnelles du client
                    Label titre1 = new Label("Informations personnelles du client sélectionné");
                    titre1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label infosPersonnellesClient = new Label(
                            "ID : " + clientSelectionne.getId() + "\n" +
                                    "Nom : " + clientSelectionne.getNom() + "\n" +
                                    "Prénom : " + clientSelectionne.getPrenom() + "\n" +
                                    "Téléphone : " + clientSelectionne.getTelephone() + "\n" +
                                    "Email : " + clientSelectionne.getEmail() + "\n" +
                                    "Adresse : " + clientSelectionne.getAdresse() + "\n" +
                                    "Date de naissance : " + clientSelectionne.getDateNaissance() + "\n" +
                                    "Nationalité : " + clientSelectionne.getNationalite() + "\n" +
                                    "Pièce d'identité : " + clientSelectionne.getTypeIdentite() + "\n" +
                                    "Numéro de pièce d'identité : " + clientSelectionne.getNumeroIdentite() + "\n" +
                                    "Remarque : " + clientSelectionne.getRemarque()
                    );

                    //"Titre 2" : Logement du client
                    Label titre2 = new Label("Logement du client sélectionné");
                    titre2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    List<Reservation> reservationClient = reservationService.getReservationsByClient(clientSelectionne.getId());
                    //Le code ci-dessous suppose que le client ne peut réserver qu'un seul logement
                    Label infosLogementClient = new Label(
                                 "Numéro de logement : " + reservationClient.get(0).getLogement().getNumero() + "\n" +
                                     "Type de logement : " + reservationClient.get(0).getLogement().getType() + "\n" +
                                     "Capacité du logement : " + reservationClient.get(0).getLogement().getCapacite() + " personnes au maximum\n" +
                                     "Nombre d'adultes prévu par le client : " + reservationClient.get(0).getNombreAdultes() + "\n" +
                                     "Nombre d'enfants prévu par le client : " + reservationClient.get(0).getNombreEnfants() + "\n" +
                                     "Disponibilité actuelle du logement : " + (reservationClient.get(0).getLogement().isDisponible() ? "OUI" : "NON") + "\n" +
                                     "Propreté actuelle du logement : " + (reservationClient.get(0).getLogement().isPropre() ? "OUI" : "NON") + "\n" +
                                     "Commentaire sur le logement : " + reservationClient.get(0).getLogement().getCommentaire() + "\n" +
                                     "Prix du logement : " + reservationClient.get(0).getLogement().getPrix() + " €\n" +
                                     "Montant payé à l'avance : " + reservationClient.get(0).getAcompte() + " €\n"
                    );

                    //"Titre 3" : Réservation du client
                    Label titre3 = new Label("Réservation du client sélectionné");
                    titre3.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    Label infosReservationClient = new Label(
                                    "Date de réservation : " + reservationClient.get(0).getDateReservation() + "\n" +
                                    "Date de début : " + reservationClient.get(0).getDateDebut() + "\n" +
                                    "Nombre de nuits : " + reservationClient.get(0).getNombreNuits() + "\n" +
                                    "Statut de la réservation : " + reservationClient.get(0).getStatut() + "\n" +
                                    "Remarque de la réservation : " + reservationClient.get(0).getRemarque() + "\n" +
                                    "Mode de paiement : " + reservationClient.get(0).getModePaiement() + "\n" +
                                    "Check in : " + reservationClient.get(0).getCheckIn() + "\n" +
                                    "Check out : " + reservationClient.get(0).getCheckOut()
                    );

                    //On assemble tout
                    content.getChildren().addAll(titre1, infosPersonnellesClient, titre2, infosLogementClient, titre3, infosReservationClient);

                    //On applique le contenu personnalisé
                    alert.getDialogPane().setContent(content);
                    alert.getButtonTypes().setAll(modifierBtn, fermerBtn);
                    alert.showAndWait();
                }
            }
        });
    }

    private void loadClients() {
        List<Client> clients = clientService.getAllClients();
        clientsData.setAll(clients);
        tableClients.setItems(clientsData);
    }


    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            ChargerPhotoProfilController popupController = loader.getController();
            popupController.setListeClientsController(this);

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

            //ChargerPhotoProfilController popupController = loader.getController();
            //popupController.setMainController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ouvrirPageCreerClient() {
        try {
            System.out.println("ouvrirPageCreerClient()");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClient.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) tableClients.getScene().getWindow(); // ← adapte ici aussi

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
