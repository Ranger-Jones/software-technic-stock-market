package com.example.stockcalc.functions;

import com.example.stockcalc.constants.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class LoadingFunctions {

    public static void showLoadingScreen(BorderPane rootPane) {

        Label loadingLabel = new Label(Text.DATA_LOADING);
        rootPane.setCenter(loadingLabel);

    }
}
