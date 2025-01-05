package com.example.stockcalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home/home.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720); // Fenstergröße: 400x300 Pixel
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stock Market Viewer");
        stage.setResizable(false); // Fenstergröße fixieren
        System.out.println(getClass().getResource("com/example/stockcalc/image/teddy.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}