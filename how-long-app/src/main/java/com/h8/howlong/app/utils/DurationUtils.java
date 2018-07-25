package com.h8.howlong.app.utils;

import java.time.Duration;

public class DurationUtils {

    public static String format(Duration d) {
        long hours = d.abs().toHours();
        long minutes = d.abs().minusHours(hours).toMinutes();
        return String.format("%s%02d:%02d",
                d.isNegative() ? "-" : "", hours, minutes);
    }

}
