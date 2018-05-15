package com.h8.howlong.utils;

import java.time.Duration;

public class DurationUtils {

    public static String format(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

}
