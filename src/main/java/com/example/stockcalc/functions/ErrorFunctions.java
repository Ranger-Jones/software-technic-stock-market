package com.example.stockcalc.functions;

import com.example.stockcalc.controller.HomeController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ErrorFunctions {
    public static void backToHome(BorderPane rootPane, FXMLLoader loader, String errorMsg) throws IOException {
        Parent detailView = loader.load();
        Platform.runLater(() -> {
            HomeController controller = loader.getController();
            controller.setError(errorMsg);
            rootPane.setCenter(detailView);
        });
    }
}
