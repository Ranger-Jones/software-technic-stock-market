package com.example.stockcalc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Hauptklasse zur Darstellung der API-Antwort von Aktienmarktdaten.
 */
public class StockMarketAPIResponse {

    // Felder der API-Antwort
    private String ticker;
    private int queryCount;
    private int resultsCount;
    private boolean adjusted;
    private List<Result> results;
    private String status;

    @JsonProperty("request_id")
    private String requestId;
    private int count;

    // Getter und Setter
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Verschachtelte Klasse zur Darstellung einzelner Ergebnisse der API-Antwort.
     */
    public static class Result {

        private double v;  // Volumen
        private double vw; // Durchschnittlicher Preis
        private double o;  // Eröffnungspreis
        private double c;  // Schlusskurs
        private double h;  // Höchstpreis
        private double l;  // Tiefstpreis
        private long t;    // Zeitstempel
        private int n;     // Anzahl der Transaktionen

        // Getter und Setter
        public double getV() {
            return v;
        }

        public void setV(double v) {
            this.v = v;
        }

        public double getVw() {
            return vw;
        }

        public void setVw(double vw) {
            this.vw = vw;
        }

        public double getO() {
            return o;
        }

        public void setO(double o) {
            this.o = o;
        }

        public double getC() {
            return c;
        }

        public void setC(double c) {
            this.c = c;
        }

        public double getH() {
            return h;
        }

        public void setH(double h) {
            this.h = h;
        }

        public double getL() {
            return l;
        }

        public void setL(double l) {
            this.l = l;
        }

        public long getT() {
            return t;
        }

        public void setT(long t) {
            this.t = t;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }
    }

    /**
     * Erstellt eine Instanz von {@link StockMarketAPIResponse} aus einem JSON-String.
     *
     * @param json Der JSON-String, der die API-Antwort repräsentiert.
     * @return Ein Objekt von {@link StockMarketAPIResponse}.
     * @throws IOException Wenn ein Fehler beim Parsen des JSON auftritt.
     */
    public static StockMarketAPIResponse fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, StockMarketAPIResponse.class);
    }
}
