package com.JESIKOM.HowManager.controllers;

import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jdk.jfr.Category;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import java.io.IOException;

@Component
public class EnregistrerClientAnnulerOuiConfirmerController {
    //un seul bouton
    @FXML private Button enregistrementSuspenduButton;



    public void voirClients(ActionEvent event){
        //ouvre ListesClients.fxml et ferme les autres fenêtres
        try {
            //Ouvrir la nouvelle fenêtre
            FXMLLoader autreLoader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
            autreLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent autreRoot = autreLoader.load();

            Stage nouvelleFenetre = new Stage();
            nouvelleFenetre.setScene(new Scene(autreRoot));
            nouvelleFenetre.setTitle("Liste des clients");
            nouvelleFenetre.show();

            /*
            //Fermer toutes les fenêtres ouvertes sauf ListesClients.fxml
            for (Window window : Window.getWindows()) {
                window.hide(); // ou window.close(); si tu préfères
            }

             */

            // Fermer toutes les fenêtres ouvertes dans le bon thread
            Platform.runLater(() -> {
                for (Window window : Window.getWindows()) {
                    if (window != nouvelleFenetre) {
                        window.hide(); // ou window.close()
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    /*
    public void voirClients(ActionEvent event){
        try {
            // Fermer toutes les fenêtres ouvertes
            for (Window window : Window.getWindows()) {
                window.hide(); // ou window.close();
            }

            // Maintenant ouvrir la nouvelle fenêtre (ListesClients.fxml)
            FXMLLoader autreLoader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
            autreLoader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
            Parent autreRoot = autreLoader.load();

            Stage nouvelleFenetre = new Stage();
            nouvelleFenetre.setScene(new Scene(autreRoot));
            nouvelleFenetre.setTitle("Liste des clients");
            nouvelleFenetre.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

     */


}
