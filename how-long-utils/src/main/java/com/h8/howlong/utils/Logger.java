package com.h8.howlong.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE_NAME = "_HowLongLog.log";

    private static final String LOG_FILE_PATH = System.getProperty("user.home");


    public static void log(String message, Object... args) {
        var m = setUpLogMessage(message, args);
        logToFile(m);
        logToConsole(m);
    }

    private static void logToFile(String message) {
        try {
            var pw = new PrintWriter(new FileWriter(new File(LOG_FILE_PATH + '\\' + LOG_FILE_NAME), true), true);
            pw.println(message);
        } catch (IOException e) {
            System.out.println("ERROR: Could not log to a file.");
            e.printStackTrace();
        }
    }

    private static void logToConsole(String message) {
        System.out.println(message);
    }

    private static String setUpLogMessage(String message, Object... args) {
        var m = message;
        for (Object a : args) {
            m = m.replaceFirst("\\{}", a.toString());
        }
        return new StringBuilder()
                .append(formatCurrentLocalDateTime())
                .append(System.lineSeparator())
                .append(m)
                .toString();
    }

    private static String formatCurrentLocalDateTime() {
        var now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return now.format(formatter);
    }
}
