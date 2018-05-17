package com.h8.howlong.utils;

import java.time.LocalDateTime;

public class DayIndexCalculator {

    public static int getDayIndex(LocalDateTime timestamp) {
        return ( timestamp.getYear() - 2000 * 1000 ) + timestamp.getDayOfYear();
    }

}
