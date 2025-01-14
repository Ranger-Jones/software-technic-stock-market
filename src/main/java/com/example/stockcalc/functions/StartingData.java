package com.example.stockcalc.functions;

import com.example.stockcalc.constants.StandardTickers;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.server.DatabaseStorage;
import com.example.stockcalc.server.StockAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility-Klasse zum Laden der Startdaten für die Anwendung.
 */
public class StartingData {

    /**
     * Lädt die notwendigen Startdaten, entweder aus der Datenbank oder durch Abruf von der API.
     *
     * @return Eine Liste von TemporarySaving-Objekten, die die geladenen Daten enthalten.
     */
    public static List<TemporarySaving> load() {
        List<TemporarySaving> list = new ArrayList<>();
        List<String> alreadyLoaded = new ArrayList<>();

        // Laden der Standard-Ticker-Daten
        StandardTickers.STANDARD_TICKERS.forEach(ticker -> {
            TemporarySaving temporarySaving = DatabaseStorage.loadTemporarySavingByTickerName(ticker);

            // Wenn keine aktuellen Daten in der Datenbank vorhanden sind, von der API abrufen
            if (temporarySaving == null || !DateFunctions.isToday(temporarySaving.getSavingDate())) {
                StockAPIClient client = new StockAPIClient();

                try {
                    temporarySaving = client.fetchDataResponse(ticker);
                } catch (IOException e) {
                    throw new RuntimeException(e); // Fehler beim API-Abruf
                }
            }

            // Hinzufügen der geladenen Daten zur Liste
            list.add(temporarySaving);
            alreadyLoaded.add(temporarySaving.getTickerName());
        });

        // Laden der letzten 10 gespeicherten Daten aus der Datenbank
        List<TemporarySaving> temporarySavings = DatabaseStorage.loadLast10Savings();
        if (temporarySavings != null && !temporarySavings.isEmpty()) {
            temporarySavings.forEach(temporarySaving -> {
                // Hinzufügen nur, wenn der Ticker nicht bereits geladen ist und die Daten aktuell sind
                if (!alreadyLoaded.contains(temporarySaving.getTickerName())
                        && DateFunctions.isToday(temporarySaving.getSavingDate())) {
                    list.add(temporarySaving);
                    alreadyLoaded.add(temporarySaving.getTickerName());
                }
            });
        }

        return list; // Zurückgabe der geladenen Daten
    }
}
