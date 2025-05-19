package com.JESIKOM.HowManager.controllers;
import com.JESIKOM.HowManager.JavaFxApplicationSupport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ListeStockController {
    public void voirListe(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EnregistrerProduit.fxml"));
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

    public void retourTableauBord(ActionEvent event){
        try {
            System.out.println("retourTableauBord()");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            loader.setControllerFactory(JavaFxApplicationSupport.getContext()::getBean);
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
