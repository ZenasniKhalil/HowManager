package com.JESIKOM.HowManager.controllers;


import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import com.JESIKOM.HowManager.models.Utilisateur;
import com.JESIKOM.HowManager.service.AuthentificationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeConnecterController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML private TextField passwordFieldVisible;

    @Autowired
    private sessionUtilisateur userSession;



    //@FXML
    //private Label erreurLabel;

    @Autowired
    private AuthentificationService authService;

    @FXML
    private void initialize() {
        emailField.setText("");
        passwordField.setText("");
        emailField.setDisable(false);
        passwordField.setDisable(false);
    }
    /*
    public void initialize(){
        emailField.requestFocus();
    }
     */

    @FXML
    public void handleConnexion() {
        String email = emailField.getText();
        //String motDePasse = passwordField.getText();
        String motDePasse = showPasswordCheckBox.isSelected()
                ? passwordFieldVisible.getText()
                : passwordField.getText();

        try {
            System.out.println("emailField " + emailField.getText());
            System.out.println("passwordField " + passwordField.getText());
            System.out.println("authService " + authService);

            Utilisateur utilisateur = authService.authentifier(email, motDePasse);
            userSession.setUtilisateurConnecte(utilisateur);
            System.out.println("Sessio utilisateur " + userSession.getUtilisateurConnecte().getNom());
            System.out.println("Bienvenue " + utilisateur.getNom());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");  // <-- modifie ici
            if(utilisateur.getNom() != null){
                alert.setHeaderText("Bienvenvue " + utilisateur.getNom());
                alert.showAndWait();
            }else{
                alert.setHeaderText("Connexion réussie ! ");
                alert.showAndWait();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);  // Injection Spring dans le FXML
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);

            MainController mainController = loader.getController();
            mainController.setUtilisateur(utilisateur);
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Échec de connexion");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    private void toggleAfficherMotDePasse() {
        if (showPasswordCheckBox.isSelected()) {
            passwordFieldVisible.setText(passwordField.getText());
            passwordFieldVisible.setVisible(true);
            passwordFieldVisible.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            passwordField.setText(passwordFieldVisible.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordFieldVisible.setVisible(false);
            passwordFieldVisible.setManaged(false);
        }
    }


}
