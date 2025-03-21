package com.JESIKOM.HowManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


public class JavaFxApplicationSupport extends Application {

    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext ctx) {
        context = ctx;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListesClients.fxml"));
        loader.setControllerFactory(context::getBean);  // Injection Spring dans le FXML

        Parent root = loader.load();
        primaryStage.setTitle("How Manager");
        primaryStage.setScene(new Scene(root, 950, 700));
        primaryStage.show();

        // Fermer Spring à la fermeture de la fenêtre
        primaryStage.setOnCloseRequest(event -> context.close());
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }
}