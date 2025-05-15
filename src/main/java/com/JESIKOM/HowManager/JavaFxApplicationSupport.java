package com.JESIKOM.HowManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import lombok.Getter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


public class JavaFxApplicationSupport extends Application {

    @Getter
    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext ctx) {
        context = ctx;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        loader.setControllerFactory(context::getBean);  // Injection Spring dans le FXML

        Parent root = loader.load();
        primaryStage.setTitle("How Manager");
        primaryStage.setScene(new Scene(root, 950, 700));
        primaryStage.show();

        // Fermer Spring à la fermeture de la fenêtre
        primaryStage.setOnCloseRequest(event -> context.close());
    }


    /*Diagramme circulaire
    @Override
    public void start(Stage primaryStage) {
        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("A", 30);
        PieChart.Data slice2 = new PieChart.Data("B", 25);
        PieChart.Data slice3 = new PieChart.Data("C", 45);

        pieChart.getData().addAll(slice1, slice2, slice3);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(pieChart);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Diagramme Circulaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    Histogramme
    @Override
    public void start(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Catégories");
        yAxis.setLabel("Valeurs");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Histogramme");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2024");
        series1.getData().add(new XYChart.Data<>("A", 30));
        series1.getData().add(new XYChart.Data<>("B", 20));
        series1.getData().add(new XYChart.Data<>("C", 40));

        barChart.getData().add(series1);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(barChart);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Histogramme");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     */

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }



}