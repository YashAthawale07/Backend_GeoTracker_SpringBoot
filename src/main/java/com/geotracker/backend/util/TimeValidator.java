package com.geotracker.backend.util;



import java.time.LocalTime;

public class TimeValidator {

    public static boolean isOfficeTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(9, 0)) &&
                now.isBefore(LocalTime.of(18, 0));
    }

    public static boolean isLate(LocalTime checkIn) {
        return checkIn.isAfter(LocalTime.of(9, 15));
    }
}
