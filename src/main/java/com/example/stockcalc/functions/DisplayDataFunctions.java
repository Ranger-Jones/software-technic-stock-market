package com.example.stockcalc.functions;

import com.example.stockcalc.controller.DetailController;
import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DisplayDataFunctions {
    public static void displayData(StockMarketAPIResponse stockValues, TickerDetailsResponse tickerDetails, BorderPane rootPane, FXMLLoader loader, FXMLLoader fallback) {
        try {
            Parent detailView = loader.load();

            DetailController controller = loader.getController();
            controller.setStockData(stockValues, tickerDetails);

            rootPane.setTop(null);
            rootPane.setCenter(detailView);
        } catch (Exception e) {
            try {
                ErrorFunctions.backToHome(rootPane, fallback, "Daten konnten nicht visualisiert werden!");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
