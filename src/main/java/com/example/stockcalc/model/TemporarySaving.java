package com.example.stockcalc.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TemporarySaving implements Serializable {
    private static final long serialVersionUID = 1L; // Empfohlen für Serializable-Klassen

    private int id;
    private LocalDateTime savingDate;
    private TickerDetailsResponse tickerDetails;
    private StockMarketAPIResponse stockData;
    private String tickerName;

    // Konstruktor
    public TemporarySaving(LocalDateTime savingDate, TickerDetailsResponse tickerDetails, StockMarketAPIResponse stockData, String tickerName) {
        this.savingDate = savingDate;
        this.tickerDetails = tickerDetails;
        this.stockData = stockData;
        this.tickerName = tickerName;
    }

    // Standard-Konstruktor (für Serialisierung erforderlich)
    public TemporarySaving() {
    }

    // Getter und Setter
    public LocalDateTime getSavingDate() {
        return savingDate;
    }

    public void setSavingDate(LocalDateTime savingDate) {
        this.savingDate = savingDate;
    }

    public TickerDetailsResponse getTickerDetails() {
        return tickerDetails;
    }

    public void setTickerDetails(TickerDetailsResponse tickerDetails) {
        this.tickerDetails = tickerDetails;
    }

    public StockMarketAPIResponse getStockData() {
        return stockData;
    }

    public void setStockData(StockMarketAPIResponse stockData) {
        this.stockData = stockData;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TemporarySaving{" +
                "savingDate=" + savingDate +
                ", tickerDetails='" + tickerDetails + '\'' +
                ", stockData='" + stockData + '\'' +
                ", tickerName='" + tickerName + '\'' +
                '}';
    }
}
