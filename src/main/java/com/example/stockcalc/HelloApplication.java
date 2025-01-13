package com.example.stockcalc;

import com.example.stockcalc.constants.FileNames;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));
        Scene scene = new Scene(loader.load(), 1280, 720); // Fenstergröße: 400x300 Pixel
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(FileNames.STYLE_SHEET)).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Stock Market Viewer");
        stage.setResizable(false); // Fenstergröße fixieren
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}