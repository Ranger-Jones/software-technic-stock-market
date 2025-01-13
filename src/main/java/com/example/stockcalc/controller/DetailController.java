package com.example.stockcalc.controller;

import com.example.stockcalc.constants.DatePattern;
import com.example.stockcalc.constants.FileNames;
import com.example.stockcalc.constants.Text;
import com.example.stockcalc.functions.NumberFormatter;
import com.example.stockcalc.functions.SearchFunctions;
import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
                {Text.CLOSE_STOCK_YESTERDAY, NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getC()), Text.USD},
                {Text.MARKET_CAP, NumberFormatter.formatLargeNumber(tickerDetails.getResults().getMarketCap()), Text.USD},
                {Text.VOLUME, NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getV()), Text.USD}
        };

        try {
            for (String[] valueSet : values) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.VALUE_CONTAINER));
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
        series.setName(Text.CHART_TITLE);

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
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DatePattern.DATE_PATTERN).withZone(zoneId);
        return formatter.format(instant);
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText();
        if (newQuery != null && !newQuery.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN));
            FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));

            SearchFunctions.search(newQuery, tickerLabel, rootPane, loader, fallback);

            topPane.getChildren().remove(searchField);
        }
    }
}
