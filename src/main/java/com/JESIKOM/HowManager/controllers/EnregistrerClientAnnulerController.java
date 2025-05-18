package com.JESIKOM.HowManager.controllers;
import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EnregistrerClientAnnulerController {
    @FXML private Button btnYes;
    @FXML private Button btnNo;
    @Autowired EnregistrerClientController enregistrerClientController;

    @FXML
    public void supprimerClient(ActionEvent event){
        System.out.println("supprimerClient()");
        System.out.println("nomField: " + enregistrerClientController.getNomField().getText());
        System.out.println("prenomField: " + enregistrerClientController.getPrenomField().getText());
        System.out.println("menuButtonTypeIdentite: " + enregistrerClientController.getMenuButtonTypeIdentite().getText());
        System.out.println("ddnField: " + enregistrerClientController.getDdnField().getValue());

        //Si tous les champs sont vides, retour à ListesClients.fxml
        if (enregistrerClientController.champsTousVides()) {
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
        else{ //Si au moins un champs est rempli
            //On prévient l'utilisateur qu'il risque de perdre les infos qu'il a entré
            //Pour cela on ouvre EnregistrerClientAnnulerOui
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EnregistrerClientAnnulerOui.fxml"));
                Parent root = fxmlLoader.load();
                EnregistrerClientAnnulerOuiController controller = fxmlLoader.getController();
                controller.setFenetrePrecedente((Stage) ((Node) event.getSource()).getScene().getWindow()); // 👈 ici
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
            //System.out.println("Ne pas ouvrir de fenêtre");
        }
        //Sinon, ouvrir EnregistrerClientAnnulerOui.fxml
    }

    @FXML
    private void onNonClicked(ActionEvent event) {
        Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.close(); // juste fermer le popup
    }
}
