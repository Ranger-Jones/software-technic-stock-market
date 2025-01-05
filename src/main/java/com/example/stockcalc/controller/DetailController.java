package com.example.stockcalc.controller;

import com.example.stockcalc.functions.NumberFormatter;
import com.example.stockcalc.functions.SearchFunctions;
import com.example.stockcalc.model.TickerDetailsResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import com.example.stockcalc.model.StockMarketAPIResponse;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DetailController {

    @FXML
    private Label tickerLabel;

    @FXML
    private TextField searchField;

    @FXML
    private LineChart<String, Number> stockChart;

    @FXML
    private BorderPane rootPane;

    @FXML
    private BorderPane topPane;

    @FXML
    private HBox valueRow;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label tickerName;

    @FXML
    private Label description;

    public void setStockData(StockMarketAPIResponse data, TickerDetailsResponse tickerDetails) {
        tickerLabel.setText(data.getTicker());
        searchField.setText(data.getTicker());
        valueRow.getChildren().clear();
        tickerName.setText(tickerDetails.getResults().getName());
        description.setText(tickerDetails.getResults().getDescription());

        String[][] values = {
                {"Close Stock yesterday", NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getC()), " USD"},
                {"Market Cap", NumberFormatter.formatLargeNumber(tickerDetails.getResults().getMarketCap()), " USD"},
                {"Volume", NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getV()), " USD"}
        };

        try {
            for (String[] valueSet : values) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/value-container.fxml"));
                Node valueContainer = loader.load();
                ValueContainerController controller = loader.getController();
                controller.setValues(valueSet[0], valueSet[1], valueSet[2]);
                valueRow.getChildren().add(valueContainer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateChart(data);
    }

    private void populateChart(StockMarketAPIResponse data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Stock Price Over Time");

        double maxValue = data.getResults().stream()
                .mapToDouble(StockMarketAPIResponse.Result::getC)
                .max()
                .orElse(0);

        data.getResults().forEach(result -> {
            String time = formatTimestamp(result.getT());
            series.getData().add(new XYChart.Data<>(time, result.getC()));
        });

        double upperBound = Math.ceil((maxValue + 100) / 100) * 100;

        NumberAxis yAxis = (NumberAxis) stockChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(upperBound);
        yAxis.setTickUnit(50);


        stockChart.getData().clear();
        stockChart.getData().add(series);
    }

    private String formatTimestamp(long timestamp) {
        java.time.Instant instant = java.time.Instant.ofEpochMilli(timestamp);
        java.time.ZoneId zoneId = java.time.ZoneId.systemDefault();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(zoneId);
        return formatter.format(instant);
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText();
        if (newQuery != null && !newQuery.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/detail.fxml"));
            FXMLLoader fallback = new FXMLLoader(getClass().getResource("/com/example/stockcalc/fxml/home/home.fxml"));

            SearchFunctions.search(newQuery, tickerLabel, rootPane, loader, fallback);

            topPane.getChildren().remove(searchField);
        }
    }
}
