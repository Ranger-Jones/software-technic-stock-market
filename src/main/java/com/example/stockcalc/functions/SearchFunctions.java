package com.example.stockcalc.functions;

import com.example.stockcalc.constants.Text;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.server.StockAPIClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Klasse zur Handhabung von Suchfunktionen im Kontext der StockCalc-Anwendung.
 */
public class SearchFunctions {

    /**
     * Führt eine Suchanfrage aus und aktualisiert die Benutzeroberfläche basierend auf den Ergebnissen.
     *
     * @param query    Der Suchbegriff, der an die API übergeben werden soll.
     * @param error    Das Label, um Fehlermeldungen anzuzeigen.
     * @param rootPane Der Hauptcontainer der Anwendung, um Inhalte zu aktualisieren.
     * @param loader   FXMLLoader, um die Anzeige der Ergebnisse zu steuern.
     * @param fallback FXMLLoader, um bei Fehlern zur Startseite zurückzukehren.
     */
    public static void search(String query, Label error, BorderPane rootPane, FXMLLoader loader, FXMLLoader fallback) {
        // Überprüfen, ob das Suchfeld leer ist
        if (query.isEmpty()) {
            error.setText(Text.EMPTY_TEXT_FIELD); // Fehlertext anzeigen, wenn kein Suchbegriff eingegeben wurde
        } else {
            // Ladebildschirm anzeigen, während die Suche ausgeführt wird
            LoadingFunctions.showLoadingScreen(rootPane);

            // Instanziieren eines API-Clients für die Datenabfrage
            StockAPIClient client = new StockAPIClient();

            // Starten eines neuen Threads für die API-Abfrage, um die Haupt-UI nicht zu blockieren
            new Thread(() -> {
                try {
                    // Abrufen der Daten von der API
                    TemporarySaving dataResponse = client.fetchDataResponse(query);

                    // Aktualisieren der UI im JavaFX-Thread mit den abgerufenen Daten
                    Platform.runLater(() -> DisplayDataFunctions.displayData(
                            dataResponse.getStockData(),
                            dataResponse.getTickerDetails(),
                            rootPane,
                            loader,
                            fallback
                    ));
                } catch (Exception e) {
                    // Fehlerbehandlung, wenn die API-Abfrage fehlschlägt
                    try {
                        // Zurück zur Startseite navigieren und Fehlermeldung anzeigen
                        ErrorFunctions.backToHome(rootPane, fallback, Text.SYMBOL_NOT_FOUND_ERROR);
                    } catch (IOException ex) {
                        // Unerwarteter Fehler: Ausnahme weiterleiten
                        throw new RuntimeException(ex);
                    }
                }
            }).start(); // Thread starten
        }
    }
}