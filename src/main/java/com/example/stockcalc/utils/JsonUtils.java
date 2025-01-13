package com.example.stockcalc.utils;

import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TickerDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    // Funktion, um ein StockMarketAPIResponse-Objekt in JSON zu konvertieren
    public static String convertStockMarketResponseToJson(StockMarketAPIResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Gibt null zurück, wenn die Konvertierung fehlschlägt
        }
    }

    // Funktion, um ein TickerDetailsResponse-Objekt in JSON zu konvertieren
    public static String convertTickerDetailsResponseToJson(TickerDetailsResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Gibt null zurück, wenn die Konvertierung fehlschlägt
        }
    }
}
