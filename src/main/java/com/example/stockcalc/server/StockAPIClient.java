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

    public TemporarySaving fetchDataResponse( String symbol) throws IOException {

        TemporarySaving temporarySaving = DatabaseStorage.loadTemporarySavingByTickerName(symbol);
        boolean notUpToDate = false;


        if(temporarySaving != null && DateFunctions.isToday(temporarySaving.getSavingDate())) {
            return temporarySaving;
        }else if (temporarySaving != null && !DateFunctions.isToday(temporarySaving.getSavingDate())){
            notUpToDate = true;
        }

        LocalDateTime now = LocalDateTime.now();
        int year = now.getMonth() == Month.JANUARY ? now.getYear() - 1 : now.getYear();
        LocalDateTime beginning = LocalDateTime.of(year, now.getMonth().minus(1), now.getDayOfMonth(), 0, 0, 0);

        String stockValueData = fetchStockData(APICalls.stockVals(symbol.toUpperCase(), beginning, now));
        String tickerDetailData = fetchStockData(APICalls.tickerDetails(symbol.toUpperCase()));

        TickerDetailsResponse tickerDetails = TickerDetailsResponse.fromJson(tickerDetailData);
        StockMarketAPIResponse stockValues = StockMarketAPIResponse.fromJson(stockValueData);

        temporarySaving = new TemporarySaving();
        temporarySaving.setSavingDate(LocalDateTime.now());
        temporarySaving.setTickerDetails(tickerDetails);
        temporarySaving.setStockData(stockValues);
        temporarySaving.setTickerName(symbol);

        if(notUpToDate){
            DatabaseStorage.updateTemporarySavingByTickerName(temporarySaving);
        }else{
            DatabaseStorage.saveTemporarySaving(temporarySaving);
        }


        return temporarySaving;
    }
}