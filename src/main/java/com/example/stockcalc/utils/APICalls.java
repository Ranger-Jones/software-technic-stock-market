package com.example.stockcalc.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class APICalls {
    public static String stockVals(String symbol, LocalDateTime beginning, LocalDateTime now) {
        Dotenv dotenv = Dotenv.load();
        return "https://api.polygon.io/v2/aggs/ticker/"
                + symbol
                + "/range/1/day/"
                + beginning.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "/"
                + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                + "?adjusted=true&sort=asc&apiKey="
                + dotenv.get("API_KEY");
    }

    public static String tickerDetails(String symbol) {
        Dotenv dotenv = Dotenv.load();
        return "https://api.polygon.io/v3/reference/tickers/" + symbol + "?apiKey=" + dotenv.get("API_KEY");
    }
}
