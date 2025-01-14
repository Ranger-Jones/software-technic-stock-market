package com.example.stockcalc.server;

import com.example.stockcalc.model.StockMarketAPIResponse;
import com.example.stockcalc.model.TemporarySaving;
import com.example.stockcalc.model.TickerDetailsResponse;
import com.example.stockcalc.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage {
    // Datenbank-URL für die SQLite-Datenbank
    private static final String DB_URL = "jdbc:sqlite:data.db";

    // Format für das Datum und die Uhrzeit im ISO-Standard
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Initialisiert die Datenbank und erstellt die Tabelle für TemporarySaving, falls diese noch nicht existiert
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // SQL-Anweisung zum Erstellen der Tabelle
            String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS temporary_saving (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            saving_date TEXT NOT NULL,
                            ticker_details TEXT,
                            stock_data TEXT,
                            ticker_name TEXT
                        )
                    """;
            // Ausführen der SQL-Anweisung zum Erstellen der Tabelle
            conn.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Problemen
            e.printStackTrace();
        }
    }

    // Speichert ein TemporarySaving-Objekt in der Datenbank
    public static void saveTemporarySaving(TemporarySaving saving) {
        String insertSQL = """
                    INSERT INTO temporary_saving (saving_date, ticker_details, stock_data, ticker_name)
                    VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Setzt die Werte für das PreparedStatement
            pstmt.setString(1, saving.getSavingDate().format(FORMATTER));  // Datum im ISO-Format
            pstmt.setString(2, JsonUtils.convertTickerDetailsResponseToJson(saving.getTickerDetails())); // Ticker Details als JSON
            pstmt.setString(3, JsonUtils.convertStockMarketResponseToJson(saving.getStockData())); // Aktien-Daten als JSON
            pstmt.setString(4, saving.getTickerName()); // Ticker-Name
            pstmt.executeUpdate(); // Ausführen des Insert-Befehls

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Problemen
            e.printStackTrace();
        }
    }

    // Lädt die letzten 10 gespeicherten TemporarySaving-Objekte aus der Datenbank
    public static List<TemporarySaving> loadLast10Savings() {
        String querySQL = "SELECT * FROM temporary_saving LIMIT 10";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            List<TemporarySaving> temporarySavings = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TemporarySaving saving = new TemporarySaving();
                saving.setId(rs.getInt("id"));
                saving.setSavingDate(LocalDateTime.parse(rs.getString("saving_date"), FORMATTER)); // Datum parsen
                saving.setTickerDetails(TickerDetailsResponse.fromJson(rs.getString("ticker_details"))); // Ticker Details aus JSON umwandeln
                saving.setStockData(StockMarketAPIResponse.fromJson(rs.getString("stock_data"))); // Aktien-Daten aus JSON umwandeln
                saving.setTickerName(rs.getString("ticker_name"));
                temporarySavings.add(saving);
            }

            return temporarySavings;
        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Problemen
            e.printStackTrace();
        } catch (IOException e) {
            // Fehlerbehandlung bei JSON-Verarbeitungsproblemen
            throw new RuntimeException(e);
        }
        return null;
    }

    // Aktualisiert ein bestehendes TemporarySaving-Objekt in der Datenbank anhand des Ticker-Namens
    public static void updateTemporarySavingByTickerName(TemporarySaving saving) {
        String updateSQL = """
                UPDATE temporary_saving
                SET saving_date = ?, ticker_details = ?, stock_data = ?
                WHERE ticker_name = ?
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // Setzt die Werte für das PreparedStatement
            pstmt.setString(1, saving.getSavingDate().format(FORMATTER));  // Datum im ISO-Format
            pstmt.setString(2, JsonUtils.convertTickerDetailsResponseToJson(saving.getTickerDetails())); // Ticker Details als JSON
            pstmt.setString(3, JsonUtils.convertStockMarketResponseToJson(saving.getStockData())); // Aktien-Daten als JSON
            pstmt.setString(4, saving.getTickerName()); // Ticker-Name
            int affectedRows = pstmt.executeUpdate(); // Ausführen des Update-Befehls

            // Optional: Ausgabe zur Bestätigung der Aktualisierung
            if (affectedRows > 0) {
                System.out.println("Daten erfolgreich für Ticker '" + saving.getTickerName() + "' aktualisiert.");
            } else {
                System.out.println("Kein Datensatz mit dem angegebenen Ticker-Namen gefunden.");
            }

        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Problemen
            e.printStackTrace();
        }
    }

    // Lädt das TemporarySaving-Objekt für einen bestimmten Ticker-Namen aus der Datenbank
    public static TemporarySaving loadTemporarySavingByTickerName(String tickerName) {
        String querySQL = "SELECT * FROM temporary_saving WHERE ticker_name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            // Setzt den Ticker-Namen als Parameter
            pstmt.setString(1, tickerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TemporarySaving saving = new TemporarySaving();
                saving.setId(rs.getInt("id"));
                saving.setSavingDate(LocalDateTime.parse(rs.getString("saving_date"), FORMATTER)); // Datum parsen
                saving.setTickerDetails(TickerDetailsResponse.fromJson(rs.getString("ticker_details"))); // Ticker Details aus JSON umwandeln
                saving.setStockData(StockMarketAPIResponse.fromJson(rs.getString("stock_data"))); // Aktien-Daten aus JSON umwandeln
                saving.setTickerName(rs.getString("ticker_name"));
                return saving;
            }
        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Problemen
            e.printStackTrace();
        } catch (IOException e) {
            // Fehlerbehandlung bei JSON-Verarbeitungsproblemen
            throw new RuntimeException(e);
        }
        return null;
    }
}
