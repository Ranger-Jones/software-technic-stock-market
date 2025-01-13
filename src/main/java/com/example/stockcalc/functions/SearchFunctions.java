package com.example.stockcalc.functions;

import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import com.example.stockcalc.server.StockAPIClient;
import com.example.stockcalc.utils.APICalls;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class SearchFunctions {
    public static void search(String query, Label error, BorderPane rootPane, FXMLLoader loader, FXMLLoader fallback){
        if (query.isEmpty()) {
            error.setText("Bitte gib einen Aktien-Symbol ein");
        } else {

            LoadingFunctions.showLoadingScreen(rootPane);
            StockAPIClient client = new StockAPIClient();
            new Thread(() -> {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    int year = now.getMonth() == Month.JANUARY ? now.getYear() - 1 : now.getYear();
                    LocalDateTime beginning = LocalDateTime.of(year, now.getMonth().minus(1), now.getDayOfMonth(), 0, 0, 0);

                    String stockValueData = client.fetchStockData(APICalls.stockVals(query.toUpperCase(), beginning, now));
                    String tickerDetailData = client.fetchStockData(APICalls.tickerDetails(query.toUpperCase()));

                    TickerDetailsResponse tickerDetails = TickerDetailsResponse.fromJson(tickerDetailData);
                    StockMarketAPIResponse stockValues = StockMarketAPIResponse.fromJson(stockValueData);
                    Platform.runLater(() -> DisplayDataFunctions.displayData(stockValues, tickerDetails, rootPane, loader, fallback));
                } catch (Exception e) {
                    try {
                        ErrorFunctions.backToHome(rootPane, fallback, "Symbol wurde nicht gefunden.");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }).start();
        }
    }
}
