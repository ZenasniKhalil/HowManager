package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Client;
import com.JESIKOM.HowManager.models.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;

import java.io.IOException;


@Component
public class EnregistrerClientAnnulerOuiController {
    @FXML private Button btnYes;
    @FXML private Button btnNo;
    private Stage fenetrePrecedente;

    @FXML
    public void supprimerClient(ActionEvent event){
        try {
            ouvrirConfirmationSuspensionEnregistrementClient(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Ouvre EnregistrerClientAnnulerOuiConfirmer.fxml
    }

    public void ouvrirConfirmationSuspensionEnregistrementClient(ActionEvent event){
        //Ouvre EnregistrerClientAnnulerOuiConfirmer.fxml

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientAnnulerOuiConfirmer.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent root = loader.load();

            Stage popup = new Stage();
            popup.setScene(new Scene(root));
            popup.setTitle("Confirmation");
            popup.setResizable(false);

            //Ce bloc s'exécute si on clique sur la CROIX
            popup.setOnCloseRequest(e -> {
                try {
                    //Ouvrir la nouvelle fenêtre
                    FXMLLoader autreLoader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
                    autreLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
                    Parent autreRoot = autreLoader.load();

                    Stage nouvelleFenetre = new Stage();
                    nouvelleFenetre.setScene(new Scene(autreRoot));
                    nouvelleFenetre.setTitle("Liste des clients");
                    nouvelleFenetre.show();

                    //Fermer toutes les fenêtres ouvertes sauf ListesClients.fxml
                    for (Window window : Window.getWindows()) {
                        window.hide(); // ou window.close(); si tu préfères
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            //Obtenir la fenêtre d'origine (celle de EnregistrerClient.fxml)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(currentStage);
            popup.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @FXML
    public void onNonClicked(ActionEvent event){
        //Retour à EnregistrerClient.fxml
        Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.close(); // juste fermer le popup

        // Fermer la fenêtre précédente si elle existe
        if (fenetrePrecedente != null) {
            fenetrePrecedente.close();
        }
    }

    public void setFenetrePrecedente(Stage stage) {
        this.fenetrePrecedente = stage;
    }
}
