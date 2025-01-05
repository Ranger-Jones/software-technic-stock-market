package com.example.stockcalc.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockAPIClient {
    public String fetchStockData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Hier kannst du die Antwort verarbeiten
                System.out.println("API-Antwort: " + response.toString());
                return response.toString();
            } else {
                System.out.println("Fehler: HTTP-Status " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Abrufen der Daten: " + e.getMessage());
            e.printStackTrace();
        }

        return "";
    }
}