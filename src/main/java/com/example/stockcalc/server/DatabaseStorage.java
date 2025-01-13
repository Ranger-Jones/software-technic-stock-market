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
    private static final String DB_URL = "jdbc:sqlite:data.db";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Initialisiert die Datenbank und erstellt die Tabelle für TemporarySaving
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS temporary_saving (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            saving_date TEXT NOT NULL,
                            ticker_details TEXT,
                            stock_data TEXT,
                            ticker_name TEXT
                        )
                    """;
            conn.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveTemporarySaving(TemporarySaving saving) {
        String insertSQL = """
                    INSERT INTO temporary_saving (saving_date, ticker_details, stock_data, ticker_name)
                    VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, saving.getSavingDate().format(FORMATTER));
            pstmt.setString(2, JsonUtils.convertTickerDetailsResponseToJson(saving.getTickerDetails()));
            pstmt.setString(3, JsonUtils.convertStockMarketResponseToJson(saving.getStockData()));
            pstmt.setString(4, saving.getTickerName());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TemporarySaving> loadLast10Savings() {
        String querySQL = "SELECT * FROM temporary_saving LIMIT 10";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {
            List<TemporarySaving> temporarySavings = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TemporarySaving saving = new TemporarySaving();
                saving.setId(rs.getInt("id"));
                saving.setSavingDate(LocalDateTime.parse(rs.getString("saving_date"), FORMATTER));
                saving.setTickerDetails(TickerDetailsResponse.fromJson(rs.getString("ticker_details")));
                saving.setStockData(StockMarketAPIResponse.fromJson(rs.getString("stock_data")));
                saving.setTickerName(rs.getString("ticker_name"));
                temporarySavings.add(saving);
            }

            return temporarySavings;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void updateTemporarySavingByTickerName(TemporarySaving saving) {
        String updateSQL = """
                UPDATE temporary_saving
                SET saving_date = ?, ticker_details = ?, stock_data = ?
                WHERE ticker_name = ?
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, saving.getSavingDate().format(FORMATTER));
            pstmt.setString(2, JsonUtils.convertTickerDetailsResponseToJson(saving.getTickerDetails()));
            pstmt.setString(3, JsonUtils.convertStockMarketResponseToJson(saving.getStockData()));
            pstmt.setString(4, saving.getTickerName());
            int affectedRows = pstmt.executeUpdate();

            // Optional: Ausgabe zur Bestätigung der Aktualisierung
            if (affectedRows > 0) {
                System.out.println("Daten erfolgreich für Ticker '" + saving.getTickerName() + "' aktualisiert.");
            } else {
                System.out.println("Kein Datensatz mit dem angegebenen Ticker-Namen gefunden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static TemporarySaving loadTemporarySavingByTickerName(String tickerName) {
        String querySQL = "SELECT * FROM temporary_saving WHERE ticker_name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setString(1, tickerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TemporarySaving saving = new TemporarySaving();
                saving.setId(rs.getInt("id"));
                saving.setSavingDate(LocalDateTime.parse(rs.getString("saving_date"), FORMATTER));
                saving.setTickerDetails(TickerDetailsResponse.fromJson(rs.getString("ticker_details")));
                saving.setStockData(StockMarketAPIResponse.fromJson(rs.getString("stock_data")));
                saving.setTickerName(rs.getString("ticker_name"));
                return saving;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
