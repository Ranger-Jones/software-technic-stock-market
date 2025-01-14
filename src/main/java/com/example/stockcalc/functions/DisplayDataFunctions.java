package com.example.stockcalc.functions;

import com.example.stockcalc.constants.Text;
import com.example.stockcalc.controller.DetailController;
import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Utility-Klasse zur Anzeige von Daten in der Benutzeroberfläche.
 */
public class DisplayDataFunctions {

    /**
     * Zeigt die Daten einer Aktienmarktabfrage in der Detailansicht an.
     *
     * @param stockValues   Die Antwort der API mit den Aktienmarktdaten.
     * @param tickerDetails Die Antwort der API mit den Ticker-Details.
     * @param rootPane      Der Hauptcontainer der Anwendung, in dem die Daten angezeigt werden sollen.
     * @param loader        Der FXMLLoader, der die Detailansicht lädt.
     * @param fallback      Der FXMLLoader, der die Startseite lädt, falls ein Fehler auftritt.
     */
    public static void displayData(StockMarketAPIResponse stockValues, TickerDetailsResponse tickerDetails, BorderPane rootPane, FXMLLoader loader, FXMLLoader fallback) {
        try {
            // Laden der Detailansicht
            Parent detailView = loader.load();

            // Zugriff auf den DetailController und Setzen der Daten
            DetailController controller = loader.getController();
            controller.setStockData(stockValues, tickerDetails);

            // Aktualisieren des RootPane, um die Detailansicht anzuzeigen
            rootPane.setTop(null);
            rootPane.setCenter(detailView);
        } catch (Exception e) {
            // Fehlerbehandlung: Rücksprung zur Startseite bei Fehlern
            try {
                ErrorFunctions.backToHome(rootPane, fallback, Text.VISUALIZE_ERROR);
            } catch (IOException ex) {
                // Unerwarteter Fehler: Ausnahme weiterleiten
                throw new RuntimeException(ex);
            }
        }
    }
}
