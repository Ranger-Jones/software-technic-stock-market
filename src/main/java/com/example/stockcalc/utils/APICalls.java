package com.example.stockcalc.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class APICalls {

    // Diese Methode erstellt die URL für den Abruf von Aktienwertdaten für das gegebene Symbol
    // basierend auf einem Zeitraum, der durch 'beginning' und 'now' definiert wird.
    public static String stockVals(String symbol, LocalDateTime beginning, LocalDateTime now) {
        // Lädt Umgebungsvariablen aus einer .env-Datei
        Dotenv dotenv = Dotenv.load();

        // Baut die URL für die API-Anfrage zusammen, einschließlich des Symbols und des Zeitraums
        return "https://api.polygon.io/v2/aggs/ticker/"
                + symbol // Ticker-Symbol
                + "/range/1/day/" // Zeitraum: 1 Tag
                + beginning.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Formatierung des Anfangsdatums
                + "/"
                + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Formatierung des Enddatums
                + "?adjusted=true&sort=asc&apiKey=" // URL-Parameter: angepasst, sortiert, API-Schlüssel
                + dotenv.get("API_KEY"); // API-Schlüssel aus den Umgebungsvariablen
    }

    // Diese Methode erstellt die URL für den Abruf von Ticker-Details für das gegebene Symbol
    public static String tickerDetails(String symbol) {
        // Lädt Umgebungsvariablen aus einer .env-Datei
        Dotenv dotenv = Dotenv.load();

        // Baut die URL für die API-Anfrage für Ticker-Details zusammen
        return "https://api.polygon.io/v3/reference/tickers/" + symbol + "?apiKey=" + dotenv.get("API_KEY");
    }
}
