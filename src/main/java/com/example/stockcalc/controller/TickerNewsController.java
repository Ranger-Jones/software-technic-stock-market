package com.example.stockcalc.controller;

import com.example.stockcalc.model.StockMarketAPIResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TickerNewsController {

    @FXML
    private Label tickerTitle;  // Label für den Ticker-Namen

    @FXML
    private Label tickerValue;  // Label für den aktuellen Wert des Tickers

    // Setzt die Ticker-Daten in die entsprechenden Label
    public void setValues(StockMarketAPIResponse stockData) {
        tickerTitle.setText(stockData.getTicker()); // Setzt den Ticker-Namen

        // Holt den letzten und den vorletzten Wert der Aktie
        double latestValue = stockData.getResults()
                .get(stockData.getResults().size() - 1).getC(); // Aktueller Wert
        double valueBeforeLatest = stockData.getResults()
                .get(stockData.getResults().size() - 2).getC(); // Wert vor dem letzten

        // Setzt den Wert des Tickers im Label
        tickerValue.setText(String.format("%.2f", latestValue));

        // Bestimmt die Farbe des Wertes basierend auf der Änderung im Wert
        if(latestValue > valueBeforeLatest) {
            tickerValue.setStyle("-fx-text-fill: #27ae60"); // Grüner Text bei steigendem Wert
        } else {
            tickerValue.setStyle("-fx-text-fill: #c0392b"); // Roter Text bei fallendem Wert
        }
    }
}
