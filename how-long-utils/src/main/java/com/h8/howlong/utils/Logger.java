package com.h8.howlong.utils;

public class Logger {

    public static void log(String message, Object... args) {
        var m = message;
        for (Object a : args) {
            m = m.replaceFirst("\\{}", a.toString());
        }
        System.out.println(m);
    }

}
