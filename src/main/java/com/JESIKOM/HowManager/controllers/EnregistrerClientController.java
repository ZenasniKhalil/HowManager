package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.LogementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.JESIKOM.HowManager.models.Logement.stringToBoolean;

@Component
public class EnregistrerClientController {
    private final EnregistrerClientValiderController enregistrerClientValiderController;
    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private Button retourListeClients;
    @FXML private Button validerButton;
    @FXML private Button annulerButton;
    @FXML private AnchorPane enregistrerClientPane;
    ListesClientsController listesClientsController;

    /*Début fx:id Client*/
    @FXML private MenuButton menuButtonTypeIdentite;
    @FXML private MenuItem menuItemCni;
    @FXML private MenuItem menuItemPasseport;
    @FXML private MenuItem menuItemCarteSejour;
    @FXML private MenuItem menuItemPermisConduire;
    private ClientService clientService;
    @FXML
    private TextField nomField, prenomField, telephoneField, emailField, adresseField, natField, numIdField;
    @FXML private TextArea remarqueArea;
    @FXML private DatePicker ddnField;
    @FXML private TextField typeIdField;
    /*Fin fx:id Client*/

    /*Début fx:id Réservation*/
    @FXML private DatePicker dateReservationField;
    @FXML private TextField heureReservationField;
    @FXML private TextField minuteReservationField;
    @FXML private DatePicker dateDebutField;
    //@FXML private DatePicker checkInField;
    @FXML private TextField checkInHeureField;
    @FXML private TextField checkInMinuteField;
    @FXML private TextField nbreNuitsField;
    @FXML private DatePicker checkOutField;
    @FXML private TextField checkOutHeureField;
    @FXML private TextField checkOutMinuteField;
    @FXML private TextField nbreEnfantsField;
    @FXML private TextField nbreAdultesField;
    @FXML private MenuButton menuButtonStatutReservation;
    @FXML private TextArea remarqueReservationField;
    @FXML private TextField acompteLogementField;
    @FXML private MenuButton modePaiementField;
    @FXML private TextField codePromoField;
    //@FXML private MenuButton modePaiementField;
    /*Fin fx:id Réservation*/

    /*Début fx:id Logement*/
    @FXML private TextField capaciteLogementField;
    @FXML private MenuButton typeLogementField;
    @FXML private TextField idLogementField;
    @FXML private TextArea commentaireLogementField;
    @FXML private TextField prixLogementField;
    @Autowired
    private LogementService logementService;
    @FXML private MenuButton menuButtonTypeLogement;
    @FXML private MenuItem menuItemAppartement;
    @FXML private MenuItem menuItemMaison;
    @FXML private MenuItem menuItemStudio;
    @FXML private MenuItem menuItemBungalow;
    @FXML private TextField dispoLogementField;
    @FXML private TextField propreLogementField;
    @FXML private MenuButton menuButtonModePaiement;
    @FXML private MenuItem menuItemEspece;
    @FXML private MenuItem menuItemVirement;
    @FXML private MenuItem menuItemMobileMoney;
    @FXML private MenuItem menuItemCarteBancaire;
    @FXML private MenuItem menuItemEnAttente;
    @FXML private MenuItem menuItemConfirmee;
    @FXML private MenuItem menuItemAnnulee;
    @FXML private MenuItem menuItemTerminee;
    /*Fin fx:id Logement*/


    public EnregistrerClientController(ClientService clientService, EnregistrerClientValiderController enregistrerClientValiderController){
        this.clientService=clientService;
        this.enregistrerClientValiderController = enregistrerClientValiderController;
    }

    @FXML
    private void ouvrirPopupConfirmation() {
        System.out.println("dans ouvrirPopConfirmation");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            // Récupérer le contrôleur de la popup
            EnregistrerClientValiderController popupController = loader.getController();
            System.out.println("controller loaded");

            // Lui passer les données du formulaire
            popupController.setClientInfos(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    adresseField.getText(),
                    ddnField.getValue(),
                    natField.getText(),
                    numIdField.getText(),
                    typeIdField.getText(),
                    remarqueArea.getText()
            );
            System.out.println("setclientInfo");

            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public void chargerPhotoProfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/chargerPhotoProfil.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.setResizable(false);

            ChargerPhotoProfilController popupController = loader.getController();
            popupController.setEnregistrerClientController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void chargerPhotoProfil(ActionEvent actionEvent) {
    }*/

    public void voirMonProfil(ActionEvent actionEvent) {
    }

    public void setListeClientController(ListesClientsController controller){
        this.listesClientsController = controller;
    }


    private void afficherAlerteChampsManquants() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText("Certains champs obligatoires ne sont pas remplis.");

        // Utilise un Text qui va s’adapter au contenu
        Text message = new Text("Seuls les champs de commentaire et de remarque ne sont pas obligatoires. " +
                                    "Veuillez remplir tous les autres champs avant de continuer.");
        message.setWrappingWidth(400); // Largeur max du texte

        // Ajoute le texte dans un pane pour qu’il s'affiche bien
        VBox dialogPaneContent = new VBox();
        dialogPaneContent.setPadding(new Insets(10));
        dialogPaneContent.getChildren().add(message);

        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
    }


    boolean champsClientEtReservationValides() {
        // Vérifie les champs Client
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || telephoneField.getText().isEmpty()
                || emailField.getText().isEmpty() || adresseField.getText().isEmpty() || ddnField.getValue() == null
                || natField.getText().isEmpty() || numIdField.getText().isEmpty()
                || menuButtonTypeIdentite.getText() == null || menuButtonTypeIdentite.getText().isEmpty()) {
            return false;
        }

        // Vérifie les champs Reservation (simplifié, tu peux adapter)
        return !(idLogementField.getText().isEmpty()
                || capaciteLogementField.getText().isEmpty()
                || dispoLogementField.getText().isEmpty()
                || propreLogementField.getText().isEmpty()
                || prixLogementField.getText().isEmpty()
                //|| dateReservationField.getValue() == null
                //|| heureReservationField.getText().isEmpty()
                //|| minuteReservationField.getText().isEmpty()
                //|| checkInField.getValue() == null
                //|| checkInHeureField.getText().isEmpty()
                //|| checkInMinuteField.getText().isEmpty()
                //|| checkOutField.getValue() == null
                //|| checkOutHeureField.getText().isEmpty()
                //|| checkOutMinuteField.getText().isEmpty()
                || dateDebutField.getValue() == null
                || nbreNuitsField.getText().isEmpty()
                || nbreAdultesField.getText().isEmpty()
                || nbreEnfantsField.getText().isEmpty()
                || acompteLogementField.getText().isEmpty()
                || menuButtonStatutReservation.getText() == null
                || menuButtonModePaiement.getText() == null);
    }


    public void ouvrirConfirmationValider() throws IOException {
        if (!champsClientEtReservationValides()) {
            afficherAlerteChampsManquants();
            return;
        }

        validerReservation(dateDebutField,
                            nbreAdultesField,
                            nbreEnfantsField,
                Integer.parseInt(capaciteLogementField.getText()));


        try {

            // Appliquer un flou sur la fenêtre principale
            BoxBlur blur = new BoxBlur(5, 5, 3);
            enregistrerClientPane.setEffect(blur);


            // Charger la popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientValider.fxml"));
            fxmlLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
            Parent popupRoot = fxmlLoader.load();
            EnregistrerClientValiderController valideur = fxmlLoader.getController();
            Client client = new Client(
                    nomField.getText(),
                    prenomField.getText(),
                    telephoneField.getText(),
                    emailField.getText(),
                    adresseField.getText(),
                    ddnField.getValue(),
                    natField.getText(),
                    numIdField.getText(),
                    TypeIdentite.labelToTypeIdentite(menuButtonTypeIdentite.getText()),
                    remarqueArea.getText()
            );
            valideur.setClient(client);

            Logement logement = new Logement(
                    Integer.parseInt(idLogementField.getText()),
                    TypeLogement.labelToTypeLogement(menuButtonTypeLogement.getText()),
                    Integer.parseInt(capaciteLogementField.getText()),
                    stringToBoolean(dispoLogementField.getText()),
                    stringToBoolean(propreLogementField.getText()),
                    commentaireLogementField.getText(),
                    Double.parseDouble(prixLogementField.getText())
            );

            //LocalDate dateResa = dateReservationField.getValue(); // récupère la date
            //int heureResa = Integer.parseInt(heureReservationField.getText());
            //int minuteResa = Integer.parseInt(minuteReservationField.getText());
            //LocalDateTime dateReservation = LocalDateTime.of(dateResa, LocalTime.of(heureResa, minuteResa));
            //LocalDate dateCheckInLocalDate = checkInField.getValue();
            //int heureCheckIn = Integer.parseInt(checkInHeureField.getText());
            //int minuteCheckIn = Integer.parseInt(checkInMinuteField.getText());
            //LocalDateTime dateCheckIn = LocalDateTime.of(dateCheckInLocalDate, LocalTime.of(heureCheckIn, minuteCheckIn));
            //LocalDate dateCheckOutLocalDate = checkOutField.getValue();
            //int heureCheckOut = Integer.parseInt(checkOutHeureField.getText());
            //int minuteCheckOut = Integer.parseInt(checkOutMinuteField.getText());
            //LocalDateTime dateCheckOut = LocalDateTime.of(dateCheckOutLocalDate, LocalTime.of(heureCheckOut, minuteCheckOut));
            Reservation reservation = new Reservation(
                    client,
                    logement,
                    LocalDateTime.now(),
                    dateDebutField.getValue(),
                    Integer.parseInt(nbreNuitsField.getText()),
                    Integer.parseInt(nbreAdultesField.getText()),
                    Integer.parseInt(nbreEnfantsField.getText()),
                    StatutReservation.labelToStatutReservation(menuButtonStatutReservation.getText()),
                    Double.parseDouble(acompteLogementField.getText()),
                    remarqueReservationField.getText(),
                    ModePaiement.labelToModePaiement(menuButtonModePaiement.getText()),
                    null,
                    null
            );
            valideur.setReservation(reservation);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.WINDOW_MODAL); // bloque interaction avec la fenêtre principale
            popupStage.initOwner(enregistrerClientPane.getScene().getWindow()); // rattacher au parent
            popupStage.setScene(new Scene(popupRoot));
            popupStage.setOnHiding(e -> {
                // Supprimer le flou lorsque la popup se ferme
                enregistrerClientPane.setEffect(null);
            });
            popupStage.setResizable(false);

            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("enregisterClientPane = " + enregistrerClientPane);
        /*debut choix typeIdentité*/
        menuItemCni.setOnAction(e -> menuButtonTypeIdentite.setText("CNI"));
        menuItemPasseport.setOnAction(e -> menuButtonTypeIdentite.setText("Passeport"));
        menuItemCarteSejour.setOnAction(e -> menuButtonTypeIdentite.setText("Carte de séjour"));
        menuItemPermisConduire.setOnAction(e -> menuButtonTypeIdentite.setText("Permis de conduire"));
        /*Fin choix typeIdentité*/

        /*debut choix typeLogement*/
        menuItemAppartement.setOnAction(e -> menuButtonTypeLogement.setText("APPARTEMENT"));
        menuItemMaison.setOnAction(e -> menuButtonTypeLogement.setText("MAISON"));
        menuItemStudio.setOnAction(e -> menuButtonTypeLogement.setText("STUDIO"));
        menuItemBungalow.setOnAction(e -> menuButtonTypeLogement.setText("BUNGALOW"));
        /*Fin choix typeLogement*/

        /*debut choix menuButtonModePaiement*/
        menuItemEspece.setOnAction(e -> menuButtonModePaiement.setText("ESPECE"));
        menuItemCarteBancaire.setOnAction(e -> menuButtonModePaiement.setText("CARTE_BANCAIRE"));
        menuItemMobileMoney.setOnAction(e -> menuButtonModePaiement.setText("MOBILE_MONEY"));
        menuItemEspece.setOnAction(e -> menuButtonModePaiement.setText("ESPECE"));
        menuItemVirement.setOnAction(e -> menuButtonModePaiement.setText("VIREMENT"));
        /*Fin choix menuButtonModePaiement*/

        /*debut choix menuButtonStatutReservation*/
        menuItemEnAttente.setOnAction(e -> menuButtonStatutReservation.setText("EN_ATTENTE"));
        menuItemConfirmee.setOnAction(e -> menuButtonStatutReservation.setText("CONFIRMEE"));
        menuItemAnnulee.setOnAction(e -> menuButtonStatutReservation.setText("ANNULEE"));
        menuItemTerminee.setOnAction(e -> menuButtonStatutReservation.setText("TERMINEE"));
        /*Fin choix menuButtonStatutReservation*/

        /*Début heures et minutes valides*/
        /*
        ajouterValidationHeure(heureReservationField, 0, 23, "heure");
        ajouterValidationHeure(minuteReservationField, 0, 59, "minute");
        ajouterValidationHeure(checkInHeureField, 0, 23, "heure");
        ajouterValidationHeure(checkInMinuteField, 0, 59, "minute");
        ajouterValidationHeure(checkOutHeureField, 0, 23, "heure");
        ajouterValidationHeure(checkOutMinuteField, 0, 59, "minute");

         */
        /*Fin heures et minutes valides*/

        /*Début dates valides*/
        ajouterValidationDateNaissance(ddnField); //le client doit être majeur
        /*Fin dates valides*/

        // Écouteur sur la capacité de logement
        capaciteLogementField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (newVal == null || newVal.isEmpty()) {
                    menuButtonTypeLogement.getItems().clear();
                    return;
                }

                int capacite = Integer.parseInt(newVal);
                if (capacite <= 0) throw new NumberFormatException();

                List<Logement> logements = logementService.getLogementDisponibleByCapacite(capacite);
                Set<TypeLogement> typesDisponibles = logements.stream()
                        .map(Logement::getType)
                        .collect(Collectors.toSet());

                menuButtonTypeLogement.getItems().clear();

                for (TypeLogement type : typesDisponibles) {
                    MenuItem item = new MenuItem(type.toString());
                    item.setOnAction(e -> {
                        menuButtonTypeLogement.setText(type.toString());

                        // Trouver le premier logement de ce type
                        logements.stream()
                                .filter(l -> l.getType().equals(type))
                                .findFirst()
                                .ifPresentOrElse(
                                        logement -> {
                                            idLogementField.setText(String.valueOf(logement.getNumero()));
                                            dispoLogementField.setText("OUI");
                                            propreLogementField.setText(logements.get(0).isPropreOuiNon());
                                            commentaireLogementField.setText(logements.get(0).getCommentaire());
                                            prixLogementField.setText(String.valueOf(logements.get(0).getPrix()));
                                        },
                                        () -> {
                                            idLogementField.setText("Null");
                                            propreLogementField.setText("Null");
                                            dispoLogementField.setText("NON");
                                        }
                                );
                    });
                    menuButtonTypeLogement.getItems().add(item);
                }

                //Réinitialiser le texte affiché
                if (!typesDisponibles.isEmpty()) {
                    menuButtonTypeLogement.setText("Choisir un type");
                } else {
                    menuButtonTypeLogement.setText("Aucun type dispo");
                }

            } catch (NumberFormatException e) {
                menuButtonTypeLogement.getItems().clear();
                menuButtonTypeLogement.setText("Capacité invalide");
                idLogementField.setText("");
                dispoLogementField.setText("");
                propreLogementField.setText("");
            }
        });
    }

    private static void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static boolean heureisValide(int heure) {
        if (heure < 0 || heure > 23) {
            afficherAlerte("Heure invalide", "L'heure doit être comprise entre 0 et 23.");            return false;
        }
        return true;
    }

    private boolean minuteisValide(int minute) {
        if (minute < 0 || minute > 59) {
            afficherAlerte("Minute invalide", "Les minutes doivent être comprises entre 0 et 59.");            return false;
        }
        return true;
    }
/*
    private void ajouterValidationHeure(TextField field, int min, int max, String label) {
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Perte de focus
                String text = field.getText().trim();
                try {
                    int valeur = Integer.parseInt(text);
                    if (valeur < min || valeur > max) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    afficherAlerteHeureInvalide(label, min, max);
                    field.setText(""); //Retirer la valeur erronée
                }
            }
        });
    }

 */

    private void afficherAlerteHeureInvalide(String label, int min, int max) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Valeur invalide");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez saisir une " + label + " valide entre " + min + " et " + max + ".");
        alert.showAndWait();
    }

    private void ajouterValidationDateNaissance(DatePicker datePicker) {
        datePicker.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Perte de focus
                LocalDate dateNaissance = datePicker.getValue();
                LocalDate aujourdHui = LocalDate.now();
                LocalDate dateLimite = aujourdHui.minusYears(18);

                if (dateNaissance == null || dateNaissance.isAfter(dateLimite)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date invalide");
                    alert.setHeaderText(null);
                    alert.setContentText("Le client doit avoir au moins 18 ans.");
                    alert.showAndWait();
                    datePicker.setValue(null); // Efface la date erronée
                }
            }
        });
    }

    private void validerReservation(DatePicker dateDebut,
                                    TextField nbAdultesField, TextField nbEnfantsField, int capaciteLogement) {
        //LocalDate dateCheckIn = checkIn.getValue();
        //LocalDate dateCheckOut = checkOut.getValue();
        LocalDate dateDebutService = dateDebut.getValue();
        LocalDate dateReser = LocalDate.now();

        // Vérifier les dates
        /*
        if (dateCheckIn == null || dateCheckOut == null) {
            afficherAlerte("Dates manquantes", "Veuillez renseigner la date de check-in et de check-out.");
            return;
        }

        if (dateCheckIn.isAfter(dateCheckOut)) {
            afficherAlerte("Erreur de dates", "La date de check-in doit être antérieure ou égale à la date de check-out.");
            return;
        }



        if (dateDebutService != null && dateDebutService.isAfter(dateCheckOut)) {
            afficherAlerte("Erreur de service", "La date de début du service doit être avant ou le jour du check-out.");
            return;
        }

        if (dateReser != null && dateReser.isAfter(dateCheckOut)) {
            afficherAlerte("Erreur de dates", "La date de réservation doit être avant ou le jour du check-out.");
            return;
        }

         */

        // Vérifier la capacité
        try {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText().trim());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText().trim());

            if (nbAdultes + nbEnfants > capaciteLogement) {
                afficherAlerte("Capacité dépassée", "Le nombre total d'occupants dépasse la capacité du logement.");
                return;
            }

        } catch (NumberFormatException e) {
            afficherAlerte("Erreur de format", "Veuillez entrer des nombres valides pour les adultes et enfants.");
        }
    }

    @FXML
    public void ouvrirConfirmationAnnuler() throws IOException {
        // Appliquer un flou sur la fenêtre principale
        BoxBlur blur = new BoxBlur(5, 5, 3);
        enregistrerClientPane.setEffect(blur);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientAnnuler.fxml"));
        fxmlLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL); // bloque interaction avec la fenêtre principale
        popupStage.initOwner(enregistrerClientPane.getScene().getWindow()); // rattacher au parent
        popupStage.setScene(new Scene(popupRoot));
        popupStage.setOnHiding(e -> {
            // Supprimer le flou lorsque la popup se ferme
            enregistrerClientPane.setEffect(null);
        });
        popupStage.setResizable(false);

        popupStage.show();
    }

    public TextField getNomField(){
        return this.nomField;
    }

    public TextField getPrenomField() {
        return this.prenomField;
    }

    public TextField getTelephoneField(){
        return telephoneField;
    }

    public TextField getEmailField(){
        return emailField;
    }

    public TextField getAdresseField(){
        return adresseField;
    }

    public MenuButton getMenuButtonTypeIdentite(){
        return menuButtonTypeIdentite;
    }

    public DatePicker getDdnField(){
        return ddnField;
    }

    public boolean champsTousVides(){
        return (nomField.getText().isEmpty() && prenomField.getText().isEmpty() && telephoneField.getText().isEmpty()
                && emailField.getText().isEmpty() && adresseField.getText().isEmpty()
                && ddnField.getValue() == null
                && natField.getText().isEmpty() && numIdField.getText().isEmpty()
                && Objects.equals(menuButtonTypeIdentite.getText(), "Sélectionner")
                && Objects.equals(menuButtonTypeLogement.getText(), "Sélectionner")
                && idLogementField.getText().isEmpty()
                && capaciteLogementField.getText().isEmpty()
                && dispoLogementField.getText().isEmpty()
                && propreLogementField.getText().isEmpty()
                //&& commentaireLogementField.getText().isEmpty()
                && prixLogementField.getText().isEmpty()
                //&& dateReservationField.getValue() == null
                //&& heureReservationField.getText().isEmpty()
                //&& minuteReservationField.getText().isEmpty()
                && dateDebutField.getValue() == null
                //&& checkInField.getValue() == null
                //&& checkInHeureField.getText().isEmpty()
                //&& checkInMinuteField.getText().isEmpty()
                //&& checkOutField.getValue() == null
                //&& checkOutHeureField.getText().isEmpty()
                //&& checkOutMinuteField.getText().isEmpty()
                && nbreNuitsField.getText().isEmpty()
                && nbreAdultesField.getText().isEmpty()
                && nbreEnfantsField.getText().isEmpty()
                //&& remarqueArea.getText().isEmpty()
                && remarqueReservationField.getText().isEmpty()
                //&& codePromoField.getText().isEmpty()
                && Objects.equals(menuButtonStatutReservation.getText(), "Sélectionner")
                && Objects.equals(menuButtonModePaiement.getText(), "Sélectionner")
                && acompteLogementField.getText().isEmpty());

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CalendrierClient"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
