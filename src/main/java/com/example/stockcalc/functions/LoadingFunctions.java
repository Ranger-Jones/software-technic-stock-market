package com.example.stockcalc.functions;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class LoadingFunctions {

    public static void showLoadingScreen(BorderPane rootPane) {

        Label loadingLabel = new Label("Daten werden geladen...");
        rootPane.setCenter(loadingLabel);

    }
}
