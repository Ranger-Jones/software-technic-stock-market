package com.example.stockcalc.functions;

import com.example.stockcalc.constants.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Utility-Klasse zur Handhabung von Ladebildschirmen in der Anwendung.
 */
public class LoadingFunctions {

    /**
     * Zeigt einen Ladebildschirm an, indem ein "Laden"-Label in der Mitte des RootPane angezeigt wird.
     *
     * @param rootPane Der Hauptcontainer der Anwendung, in dem der Ladebildschirm angezeigt wird.
     */
    public static void showLoadingScreen(BorderPane rootPane) {
        // Erstellen eines Labels mit dem Text für das Laden
        Label loadingLabel = new Label(Text.DATA_LOADING);

        // Platzieren des Labels in der Mitte des RootPane
        rootPane.setCenter(loadingLabel);
    }
}
