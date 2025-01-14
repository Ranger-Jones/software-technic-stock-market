package com.example.stockcalc.functions;

/**
 * Utility-Klasse zur Formatierung von großen Zahlen in kompaktere Darstellungen.
 */
public class NumberFormatter {

    /**
     * Formatiert eine Zahl in eine kompakte Darstellung mit entsprechenden Einheiten (z. B. Tausend, Million).
     *
     * @param number Die zu formatierende Zahl.
     * @return Eine formatierte Zeichenkette, die die Zahl mit der entsprechenden Einheit darstellt.
     */
    public static String formatLargeNumber(double number) {
        if (number >= 1_000_000_000_000_000_000L) { // Billiarde (Brd)
            return String.format("%.2fBrd", number / 1_000_000_000_000_000_000L);
        } else if (number >= 1_000_000_000_000_000L) { // Billion (B)
            return String.format("%.2fB", number / 1_000_000_000_000_000L);
        } else if (number >= 1_000_000_000_000L) { // Milliarde (Mrd)
            return String.format("%.2fT", number / 1_000_000_000_000L);
        } else if (number >= 1_000_000_000L) { // Milliarden (Mrd)
            return String.format("%.2fMrd", number / 1_000_000_000L);
        } else if (number >= 1_000_000L) { // Millionen (M)
            return String.format("%.2fM", number / 1_000_000L);
        } else if (number >= 1_000L) { // Tausend (T)
            return String.format("%.2fT", number / 1_000L);
        } else {
            return String.format("%.2f", number); // Zahl bleibt unverändert
        }
    }
}
