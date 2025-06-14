package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.LogementService;
import com.JESIKOM.HowManager.service.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListesClientsController {
    public Button enregistrerNouveauClient;
    @FXML
    private MenuItem menuItem1;
    @FXML
    private MenuItem menuItem2;
    @FXML
    private MenuItem menuItem3;
    @FXML
    private MenuItem voirMonProfilButton;
    @FXML
    Button profileButton; // Récupère le bouton
    @FXML
    private ImageView profileImage;
    @FXML
    private MenuButton nomUtilisateur;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LogementService logementService;

    /*Debut table Client*/
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
    private ObservableList<Client> clientsData = FXCollections.observableArrayList();
    /*Fin table Client*/

    /*Debut table Réservation*/
    @FXML
    private TableView<Reservation> tableReservations;
    @FXML
    private TableColumn<Reservation, Long> colIDResa;
    @FXML
    private TableColumn<Reservation, String> colIDClient;
    @FXML
    private TableColumn<Reservation, String> colIDLogement;
    @FXML
    private TableColumn<Reservation, String> colDateDebut;
    @FXML
    private TableColumn<Reservation, String> colNbreNuits;
    @FXML
    private TableColumn<Reservation, String> colStatut;
    @FXML
    private TableColumn<Reservation, String> colAcompte;
    @FXML
    private TableColumn<Reservation, String> colRemarqueResa;
    @FXML
    private TableColumn<Reservation, String> colCheckIn;
    @FXML
    private TableColumn<Reservation, String> colCheckOut;
    private ObservableList<Reservation> resaData = FXCollections.observableArrayList();
    /*Fin table Réservation*/

    /*Debut table Logement*/
    @FXML
    private TableView<Logement> tableLogements;
    @FXML
    private TableColumn<Logement, Integer> colLogementID;
    @FXML
    private TableColumn<Logement, String> colTypeLogement;
    @FXML
    private TableColumn<Logement, String> colCapciteLogement;
    @FXML
    private TableColumn<Logement, String> colDisponibleLogement;
    @FXML
    private TableColumn<Logement, String> colPropreLogement;
    @FXML
    private TableColumn<Logement, String> colCommentaireLogement;
    @FXML
    private TableColumn<Logement, String> colPrixLogement;
    private ObservableList<Logement> logementData = FXCollections.observableArrayList();
    /*Fin table Logement*/

    @FXML
    private Button tableauBord;

    @FXML
    private TextField searchField;
    private Utilisateur utilisateur;

    @Autowired
    private sessionUtilisateur userSession;


/*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userSession != null && userSession.getUtilisateurConnecte() != null) {
            nomUtilisateur.setText(userSession.getUtilisateurConnecte().getNom());
        } else {
            nomUtilisateur.setText("Utilisateur");
        }
    }

 */

    @FXML
    public void initialize() {
        if (userSession != null && userSession.getUtilisateurConnecte() != null) {
            nomUtilisateur.setText(userSession.getUtilisateurConnecte().getNom());
        } else {
            nomUtilisateur.setText("Utilisateur");
        }
        //Rendre l'image cliquable pour rouvrir la popup
        profileImage.setOnMouseClicked(event -> chargerPhotoProfil());

        /*Début initialisation table Client*/
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
        /*Fin initialisation table Client*/

        /*Début initialisation table Réservation*/
        //Associer les propriétés de Client aux colonnes
        colIDResa.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIDClient.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getClient().getId())));
        colIDLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getLogement().getNumero())));
        colDateDebut.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getDateDebut())));
        colNbreNuits.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getNombreNuits())));
        colStatut.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getStatut())));
        colAcompte.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getAcompte())));
        colRemarqueResa.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRemarque()));
        colCheckIn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCheckIn() != null ? String.valueOf(cellData.getValue().getCheckIn()) : "Non fait"));
        colCheckOut.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCheckOut() != null ? String.valueOf(cellData.getValue().getCheckOut()) : "Non fait"));
        loadReservations();

        tableReservations.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                Reservation reservationSelectionnee = tableReservations.getSelectionModel().getSelectedItem();
                if (reservationSelectionnee != null) {
                    showActionAlert(reservationSelectionnee);
                }
            }
        });
        /*Fin initialisation table Réservation*/

        /*Début initialisation table Logement*/
        //Associer les propriétés de Client aux colonnes
        colLogementID.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTypeLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getType())));
        colCapciteLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCapacite())));
        colDisponibleLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().isDisponible())));
        colPropreLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().isPropre())));
        colCommentaireLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCommentaire())));
        colPrixLogement.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getPrix())));
        loadLogements();
        /*Fin initialisation table Logement*/

    }

    private void showConfirmationAlert(String operation, Reservation reservation) {
        // Création de l'alerte de confirmation
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Confirmer l'opération");
        confirmAlert.setContentText("Êtes-vous sûr de vouloir effectuer l'opération " + operation + " ?");

        // Ajout des boutons standard "OK" et "Annuler"
        ButtonType confirmButton = ButtonType.OK;
        ButtonType cancelButton = ButtonType.CANCEL;
        confirmAlert.getButtonTypes().setAll(confirmButton, cancelButton);
        //Reservation reservationSelectionnee = tableReservations.getSelectionModel().getSelectedItem();


        // Affichage de l'alerte de confirmation et traitement du résultat
        confirmAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == confirmButton) {
                System.out.println("Confirmation bouton ok clic");
                // L'utilisateur a confirmé, exécuter le service
                processService(operation, reservation);
            }
            // Quoi qu'il arrive, la première alerte est déjà fermée
        });
    }

    private void showActionAlert(Reservation reservation) {
        // Création de l'alerte principale avec les trois boutons
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action réservation");
        alert.setHeaderText("Veuillez choisir une opération");
        alert.setContentText("Sélectionnez l'opération que vous souhaitez effectuer :");

        // Création des boutons personnalisés
        ButtonType checkInButton = new ButtonType("Check in", ButtonBar.ButtonData.YES);
        ButtonType checkOutButton = new ButtonType("Check out", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Ajout des boutons à l'alerte
        alert.getButtonTypes().setAll(checkInButton, checkOutButton, cancelButton);

        // Affichage de l'alerte et traitement du résultat
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == checkInButton) {
                System.out.println("Check in clic");
                showConfirmationAlert("Check in", reservation);
            } else if (buttonType == checkOutButton) {
                System.out.println("Check out clic");
                showConfirmationAlert("Check out", reservation);
            }
            // Si c'est "Annuler", on ne fait rien
        });
    }

    private void processService(String operation, Reservation reservation){
        try {
            if(operation == "Check in"){
                System.out.println("reservationService : " + reservationService);
                reservationService.checkIn(reservation.getId());
                System.out.println(reservationService.getReservationById(reservation.getId()).get().getCheckIn().getMonth());
            }else if(operation == "Check out"){
                reservationService.checkOut(reservation.getId());
                System.out.println(reservationService.getReservationById(reservation.getId()).get().getCheckOut());
                System.out.println(reservationService.getReservationById(reservation.getId()).get().getCheckOut().getMonth());
            }else{
                System.out.println("Pas bonne route !!");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Erreur : réservation non trouvée !" + e.getMessage());
        }
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        if (nomUtilisateur != null && utilisateur != null) {
            nomUtilisateur.setText(utilisateur.getNom());
        }
    }


    private void loadClients() {
        List<Client> clients = clientService.getAllClients();
        clientsData.setAll(clients);
        tableClients.setItems(clientsData);
    }

    private void loadReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        resaData.setAll(reservations);
        tableReservations.setItems(resaData);
    }

    private void loadLogements() {
        List<Logement> logements = logementService.getAllLogements();
        logementData.setAll(logements);
        tableLogements.setItems(logementData);
    }


    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
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

    public void voirMonProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/voirMonProfil.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
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

    public void retourTableauBord() {
        try {
            System.out.println("retourTableauBord()");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
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

    public void ouvrirPersonnel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Personnel.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ouvrirPlanning(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CalendrierClient.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

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
