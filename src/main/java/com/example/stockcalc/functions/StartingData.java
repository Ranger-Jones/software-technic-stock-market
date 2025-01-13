package com.example.stockcalc.functions;

import com.example.stockcalc.constants.StandardTickers;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.server.DatabaseStorage;
import com.example.stockcalc.server.StockAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartingData {
    public static List<TemporarySaving> load() {
        List<TemporarySaving> list = new ArrayList<>();
        List<String> alreadyLoaded = new ArrayList<>();

        StandardTickers.STANDARD_TICKERS.forEach(ticker ->
        {
            TemporarySaving temporarySaving = DatabaseStorage.loadTemporarySavingByTickerName(ticker);

            if (temporarySaving == null || !DateFunctions.isToday(temporarySaving.getSavingDate())) {
                StockAPIClient client = new StockAPIClient();

                try {
                    temporarySaving = client.fetchDataResponse(ticker);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            list.add(temporarySaving);
            alreadyLoaded.add(temporarySaving.getTickerName());
        });

        List<TemporarySaving> temporarySavings = DatabaseStorage.loadLast10Savings();
        if (temporarySavings != null && !temporarySavings.isEmpty()) {
            temporarySavings.forEach(temporarySaving ->
            {
                if (!alreadyLoaded.contains(temporarySaving.getTickerName()) && DateFunctions.isToday(temporarySaving.getSavingDate())) {
                    list.add(temporarySaving);
                    alreadyLoaded.add(temporarySaving.getTickerName());
                }
            });
        }

        return list;
    }
}
