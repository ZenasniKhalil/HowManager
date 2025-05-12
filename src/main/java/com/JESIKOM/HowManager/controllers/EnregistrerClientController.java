package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.*;
import com.JESIKOM.HowManager.service.ClientService;
import com.JESIKOM.HowManager.service.LogementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnregistrerClientController {
    private final EnregistrerClientValiderController enregistrerClientValiderController;
    @FXML Button profileButton; // Récupère le bouton
    @FXML private ImageView profileImage;
    @FXML private MenuItem voirMonProfilButton;
    @FXML private Button retourListeClients;
    @FXML private Button validerButton;
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
    @FXML private DatePicker checkInField;
    @FXML private TextField checkInHeureField;
    @FXML private TextField checkInMinuteField;
    @FXML private TextField nbreNuitsField;
    @FXML private DatePicker CheckOutField;
    @FXML private TextField checkOutHeureField;
    @FXML private TextField checkOutMinuteField;
    @FXML private TextField nbreEnfantsField;
    @FXML private TextField nbreAdultesField;
    @FXML private MenuButton statutReservationField;
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
    @FXML private MenuButton menuButtonStatutReservation;
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

    public void ouvrirListeClients(){
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
            Parent root = loader.load();

            //Obtenir le Stage actuel
            Stage stage = (Stage) tableClients.getScene().getWindow(); // ← adapte ici aussi

            stage.setScene(new Scene(root)); // Remplacer le contenu
            stage.show(); // si nécessaire

        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }

    public void ouvrirConfirmationValider() throws IOException {
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

            // Vérification et parsing de la capacité
            int capaciteRequise;
            try {
                capaciteRequise = Integer.parseInt(capaciteLogementField.getText());
                if (capaciteRequise <= 0) {
                    throw new NumberFormatException("Capacité non positive");
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer une capacité valide (nombre entier strictement positif).");
                alert.showAndWait();
                enregistrerClientPane.setEffect(null); // On retire le flou
                return; // On arrête l'exécution ici
            }

            // Récupérer la liste des logements disponibles avec capacité suffisante
            List<Logement> logementsDisponibles = logementService.getLogementDisponibleByCapacite(capaciteRequise);

            if (!logementsDisponibles.isEmpty()) {
                Logement premierLogement = logementsDisponibles.get(0);

                // Mettre à jour les champs dans l'interface
                typeLogementField.setText(premierLogement.getType().toString());
                idLogementField.setText(String.valueOf(premierLogement.getNumero()));

                // Optionnel : envoyer à la popup
                valideur.setLogement(premierLogement);
                System.out.println();
            } else {
                // Aucun logement disponible trouvé
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun logement disponible");
                alert.setHeaderText(null);
                alert.setContentText("Aucun logement disponible avec la capacité requise.");
                alert.showAndWait();
            }


            //On recupère la liste de logements disponibles
            //On ne garde que ceux qui ont une capacité >= capaciteLogementField
            //Pour cela utiliser getLogementDisponibleByCapacite
            //On prend le premier de la liste, on met son type dans typeLogementField (  et son numéro dans idLogementField
            //Logement logementsdisponibles = new Logement()
            //Reservation reservation = new Reservation();
            //valideur.setReservation(reservation);

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
}
