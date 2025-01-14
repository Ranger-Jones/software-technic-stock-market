package com.example.stockcalc.server;

import com.example.stockcalc.functions.DateFunctions;
import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.model.TickerDetailsResponse;
import com.example.stockcalc.utils.APICalls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;

public class StockAPIClient {

    // Diese Methode ruft die Stock-Daten von einer gegebenen URL ab und gibt die Antwort als String zurück
    public String fetchStockData(String urlString) {
        try {
            // Erstellen der URL-Verbindung und Setzen der HTTP-Anforderungsmethode auf "GET"
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Abrufen des Antwortcodes der API-Anfrage
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // Erfolgreiche Antwort
                // Lesen der Antwort der API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Gibt die Antwort als String zurück
                System.out.println("API-Antwort: " + response.toString());
                return response.toString();
            } else {
                // Fehlerbehandlung bei nicht erfolgreicher Antwort
                System.out.println("Fehler: HTTP-Status " + responseCode);
            }
        } catch (Exception e) {
            // Fehlerbehandlung bei Ausnahmefehlern
            System.out.println("Fehler beim Abrufen der Daten: " + e.getMessage());
            e.printStackTrace();
        }

        return "";
    }

    // Diese Methode ruft Daten ab und gibt ein TemporarySaving-Objekt zurück
    public TemporarySaving fetchDataResponse(String symbol) throws IOException {

        // Zuerst wird das temporäre Speichern für den gegebenen Ticker aus der Datenbank geladen
        TemporarySaving temporarySaving = DatabaseStorage.loadTemporarySavingByTickerName(symbol);
        boolean notUpToDate = false;

        // Überprüfen, ob die Daten aktuell sind (ob sie heute gespeichert wurden)
        if (temporarySaving != null && DateFunctions.isToday(temporarySaving.getSavingDate())) {
            return temporarySaving; // Wenn die Daten heute gespeichert wurden, zurückgeben
        } else if (temporarySaving != null && !DateFunctions.isToday(temporarySaving.getSavingDate())) {
            notUpToDate = true; // Wenn die Daten nicht heute gespeichert wurden, wird ein Flag gesetzt
        }

        // Berechnen des Datumsbereichs für den letzten Monat
        LocalDateTime now = LocalDateTime.now();
        int year = now.getMonth() == Month.JANUARY ? now.getYear() - 1 : now.getYear(); // Jahr anpassen, wenn es Januar ist
        LocalDateTime beginning = LocalDateTime.of(year, now.getMonth().minus(1), now.getDayOfMonth(), 0, 0, 0);

        // Abrufen der Stock- und Ticker-Details-Daten von den APIs
        String stockValueData = fetchStockData(APICalls.stockVals(symbol.toUpperCase(), beginning, now));
        String tickerDetailData = fetchStockData(APICalls.tickerDetails(symbol.toUpperCase()));

        // Umwandeln der abgerufenen JSON-Daten in die entsprechenden Modelle
        TickerDetailsResponse tickerDetails = TickerDetailsResponse.fromJson(tickerDetailData);
        StockMarketAPIResponse stockValues = StockMarketAPIResponse.fromJson(stockValueData);

        // Erstellen eines neuen TemporarySaving-Objekts mit den abgerufenen Daten
        temporarySaving = new TemporarySaving();
        temporarySaving.setSavingDate(LocalDateTime.now()); // Speichern des aktuellen Zeitpunkts
        temporarySaving.setTickerDetails(tickerDetails); // Speichern der Ticker-Details
        temporarySaving.setStockData(stockValues); // Speichern der Aktien-Daten
        temporarySaving.setTickerName(symbol); // Speichern des Ticker-Namens

        // Wenn die Daten nicht aktuell waren, werden sie in der Datenbank aktualisiert, andernfalls werden sie gespeichert
        if (notUpToDate) {
            DatabaseStorage.updateTemporarySavingByTickerName(temporarySaving); // Update der bestehenden Daten
        } else {
            DatabaseStorage.saveTemporarySaving(temporarySaving); // Speichern der neuen Daten
        }

        // Gibt das TemporarySaving-Objekt zurück
        return temporarySaving;
    }
}
