package com.example.stockcalc.functions;

import java.time.LocalDateTime;

/**
 * Utility-Klasse zur Handhabung von Datumsfunktionen.
 */
public class DateFunctions {

    /**
     * Überprüft, ob ein gegebenes Datum dem aktuellen Tag entspricht.
     *
     * @param date Das zu überprüfende Datum.
     * @return {@code true}, wenn das Datum dem heutigen Tag entspricht; andernfalls {@code false}.
     */
    public static boolean isToday(LocalDateTime date) {
        return date.getDayOfMonth() == LocalDateTime.now().getDayOfMonth()
                && date.getMonth() == LocalDateTime.now().getMonth()
                && date.getYear() == LocalDateTime.now().getYear();
    }
}