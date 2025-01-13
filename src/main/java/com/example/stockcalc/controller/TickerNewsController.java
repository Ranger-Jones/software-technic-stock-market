package com.example.stockcalc.controller;

import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TickerNewsController {
    @FXML
    private Label tickerTitle;

    @FXML
    private Label tickerValue;

    public void setValues(StockMarketAPIResponse stockData) {
        tickerTitle.setText(stockData.getTicker());

        double latestValue = stockData.getResults()
                .get(stockData.getResults().size() - 1).getC();
        double valueBeforeLatest = stockData.getResults()
                .get(stockData.getResults().size() - 2).getC();

        tickerValue.setText(String.format("%.2f", stockData.getResults()
                .get(stockData.getResults().size() - 1).getC()));

        if(latestValue > valueBeforeLatest) {
            tickerValue.setStyle("-fx-text-fill: #27ae60");
        }else{
            tickerValue.setStyle("-fx-text-fill: #c0392b");
        }

    }
}
