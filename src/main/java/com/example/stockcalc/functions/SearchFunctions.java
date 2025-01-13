package com.example.stockcalc.functions;

import com.example.stockcalc.constants.Text;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.server.StockAPIClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SearchFunctions {
    public static void search(String query, Label error, BorderPane rootPane, FXMLLoader loader, FXMLLoader fallback) {
        if (query.isEmpty()) {
            error.setText(Text.EMPTY_TEXT_FIELD);
        } else {

            LoadingFunctions.showLoadingScreen(rootPane);
            StockAPIClient client = new StockAPIClient();
            new Thread(() -> {
                try {
                    TemporarySaving dataResponse = client.fetchDataResponse(query);
                    Platform.runLater(() -> DisplayDataFunctions.displayData(dataResponse.getStockData(), dataResponse.getTickerDetails(), rootPane, loader, fallback));
                } catch (Exception e) {
                    try {
                        ErrorFunctions.backToHome(rootPane, fallback, Text.SYMBOL_NOT_FOUND_ERROR);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }).start();
        }
    }
}
