package com.h8.howlong.utils;

import java.time.Duration;

public class DurationUtils {

    public static String format(Duration d) {
        long hours = d.toHours();
        long minutes = d.minusHours(hours).toMinutes();
        return String.format("%02d:%02d", hours, minutes);
    }

}
