package com.example.stockcalc.functions;

import com.example.stockcalc.controller.HomeController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Utility-Klasse zur Handhabung von Fehlern und zur Navigation zur Startseite der Anwendung.
 */
public class ErrorFunctions {

    /**
     * Navigiert zur Startseite und zeigt eine Fehlermeldung an.
     *
     * @param rootPane Der Hauptcontainer der Anwendung, in dem der neue Inhalt angezeigt wird.
     * @param loader   Der FXMLLoader, der für das Laden der Startseitenansicht verwendet wird.
     * @param errorMsg Die anzuzeigende Fehlermeldung.
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt.
     */
    public static void backToHome(BorderPane rootPane, FXMLLoader loader, String errorMsg) throws IOException {
        // Laden der FXML-Datei für die Startseitenansicht
        Parent detailView = loader.load();

        // Aktualisieren der Benutzeroberfläche im JavaFX-Thread
        Platform.runLater(() -> {
            // Zugriff auf den HomeController und Setzen der Fehlermeldung
            HomeController controller = loader.getController();
            controller.setError(errorMsg);

            // Platzieren der Startseitenansicht im Hauptcontainer
            rootPane.setCenter(detailView);
        });
    }
}
