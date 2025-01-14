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
    private Label tickerLabel; // Label zur Anzeige des Tickersymbols

    @FXML
    private TextField searchField; // Textfeld zur Eingabe des Tickersymbols für eine Suche

    @FXML
    private LineChart<String, Number> stockChart; // Diagramm zur Anzeige von Aktienkursen

    @FXML
    private BorderPane rootPane; // Hauptlayout für die Detailansicht

    @FXML
    private BorderPane topPane; // Layout für den oberen Bereich, der unter anderem das Suchfeld enthält

    @FXML
    private HBox valueRow; // Container für die Anzeige von Aktienwerten

    @FXML
    private Label tickerName; // Label für den Namen des Unternehmens/Tickers

    @FXML
    private Label description; // Label für die Beschreibung des Unternehmens

    // Diese Methode setzt die Stock-Daten und Ticker-Details auf der Detailseite
    public void setStockData(StockMarketAPIResponse data, TickerDetailsResponse tickerDetails) {
        // Setzt das Tickersymbol und das Suchfeld
        tickerLabel.setText(data.getTicker());
        searchField.setText(data.getTicker());

        // Leert die Row, bevor neue Werte hinzugefügt werden
        valueRow.getChildren().clear();

        // Setzt den Ticker-Namen und die Beschreibung
        tickerName.setText(tickerDetails.getResults().getName());
        description.setText(tickerDetails.getResults().getDescription());

        // Erzeugt Werte für die Anzeige in der ValueRow (Aktueller Kurs, Marktkapitalisierung und Volumen)
        String[][] values = {
                {Text.CLOSE_STOCK_YESTERDAY, NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getC()), Text.USD},
                {Text.MARKET_CAP, NumberFormatter.formatLargeNumber(tickerDetails.getResults().getMarketCap()), Text.USD},
                {Text.VOLUME, NumberFormatter.formatLargeNumber(data.getResults().get(data.getResults().size() - 1).getV()), Text.USD}
        };

        // Versucht, für jedes Value-Paar eine neue ValueContainer-Ansicht zu laden und hinzuzufügen
        try {
            for (String[] valueSet : values) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.VALUE_CONTAINER)); // Lädt die FXML-Datei für den ValueContainer
                Node valueContainer = loader.load(); // Lädt den Wertcontainer
                ValueContainerController controller = loader.getController(); // Holt den Controller des Containers
                controller.setValues(valueSet[0], valueSet[1], valueSet[2]); // Setzt die Werte für den Container
                valueRow.getChildren().add(valueContainer); // Fügt den Container der Row hinzu
            }
        } catch (IOException e) {
            e.printStackTrace(); // Fehlerbehandlung
        }

        // Füllt das Chart mit den Stock-Daten
        populateChart(data);
    }

    // Diese Methode füllt das Diagramm mit Aktienkursen (auf Basis der API-Daten)
    private void populateChart(StockMarketAPIResponse data) {
        XYChart.Series<String, Number> series = new XYChart.Series<>(); // Erstellt eine Serie für das Diagramm
        series.setName(Text.CHART_TITLE); // Setzt den Titel des Diagramms

        // Bestimmt den maximalen Wert aus den Kursen, um die obere Grenze des Y-Achsenbereichs zu setzen
        double maxValue = data.getResults().stream()
                .mapToDouble(StockMarketAPIResponse.Result::getC) // Extrahiert den Kurs
                .max()
                .orElse(0); // Falls keine Kurse vorhanden sind, wird 0 als Default verwendet

        // Fügt die Datenpunkte (Kurs über Zeit) der Serie hinzu
        data.getResults().forEach(result -> {
            String time = formatTimestamp(result.getT()); // Formatiert den Zeitstempel
            series.getData().add(new XYChart.Data<>(time, result.getC())); // Fügt die Zeit und den Kurs der Serie hinzu
        });

        // Setzt die obere Grenze der Y-Achse basierend auf dem höchsten Wert
        double upperBound = Math.ceil((maxValue + 100) / 100) * 100;
        NumberAxis yAxis = (NumberAxis) stockChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(upperBound);
        yAxis.setTickUnit(50); // Setzt den Abstand zwischen den Ticks auf der Y-Achse

        // Fügt die Serie zum Diagramm hinzu
        stockChart.getData().clear();
        stockChart.getData().add(series);
    }

    // Diese Methode formatiert den Zeitstempel in ein lesbares Datumsformat
    private String formatTimestamp(long timestamp) {
        java.time.Instant instant = java.time.Instant.ofEpochMilli(timestamp);
        java.time.ZoneId zoneId = java.time.ZoneId.systemDefault();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DatePattern.DATE_PATTERN).withZone(zoneId);
        return formatter.format(instant);
    }

    // Diese Methode wird ausgelöst, wenn der Benutzer die Eingabetaste im Suchfeld drückt
    @FXML
    public void onEnter(ActionEvent ae) {
        String newQuery = searchField.getText(); // Holt den Text aus dem Suchfeld
        if (newQuery != null && !newQuery.isEmpty()) { // Wenn das Suchfeld nicht leer ist
            // Lädt das entsprechende FXML für die Detailansicht und die Home-Ansicht
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FileNames.DETAIL_SCREEN));
            FXMLLoader fallback = new FXMLLoader(getClass().getResource(FileNames.HOME_SCREEN));

            // Ruft die Suchfunktion auf und übergibt die FXML-Loader
            SearchFunctions.search(newQuery, tickerLabel, rootPane, loader, fallback);

            // Entfernt das Suchfeld aus dem oberen Bereich, nachdem die Suche abgeschlossen ist
            topPane.getChildren().remove(searchField);
        }
    }
}
