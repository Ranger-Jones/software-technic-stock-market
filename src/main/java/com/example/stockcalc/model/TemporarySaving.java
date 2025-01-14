package com.example.stockcalc.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Klasse, die temporäre Speicherdaten repräsentiert, um API-Antworten und Ticker-Details zwischenzuspeichern.
 * Diese Klasse implementiert {@link Serializable}, um die Objekte serialisierbar zu machen.
 */
public class TemporarySaving implements Serializable {

    private static final long serialVersionUID = 1L; // Versionierung für Serializable-Klassen

    private int id; // Eindeutige ID der Speicherung
    private LocalDateTime savingDate; // Zeitpunkt der Speicherung
    private TickerDetailsResponse tickerDetails; // Details des Tickers
    private StockMarketAPIResponse stockData; // Daten des Aktienmarktes
    private String tickerName; // Name des Tickers

    /**
     * Konstruktor, um alle Felder zu initialisieren.
     *
     * @param savingDate Zeitpunkt der Speicherung.
     * @param tickerDetails Details des Tickers.
     * @param stockData Daten des Aktienmarktes.
     * @param tickerName Name des Tickers.
     */
    public TemporarySaving(LocalDateTime savingDate, TickerDetailsResponse tickerDetails, StockMarketAPIResponse stockData, String tickerName) {
        this.savingDate = savingDate;
        this.tickerDetails = tickerDetails;
        this.stockData = stockData;
        this.tickerName = tickerName;
    }

    /**
     * Standard-Konstruktor (erforderlich für Serialisierung und Deserialisierung).
     */
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

    /**
     * Überschreibt die Standard-Implementierung von {@link Object#toString()} für eine lesbare Darstellung der Objektdaten.
     */
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
