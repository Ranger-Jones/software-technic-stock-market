package com.example.stockcalc.functions;

import java.time.LocalDateTime;

public class DateFunctions {
    public static boolean isToday(LocalDateTime date) {
        return date.getDayOfMonth() == LocalDateTime.now().getDayOfMonth() && date.getMonth() == LocalDateTime.now().getMonth();
    }
}
