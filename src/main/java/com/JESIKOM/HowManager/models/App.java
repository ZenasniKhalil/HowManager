package com.JESIKOM.HowManager.models;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}