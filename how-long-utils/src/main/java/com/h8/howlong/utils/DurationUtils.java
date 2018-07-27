package com.h8.howlong.utils;

import java.time.Duration;

public class DurationUtils {

    public static String format(Duration d) {
        var hours = d.abs().toHours();
        var minutes = d.abs().minusHours(hours).toMinutes();
        return String.format("%s%02d:%02d",
                d.isNegative() ? "-" : "", hours, minutes);
    }

}
