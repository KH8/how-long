package com.h8.howlong.utils;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final Path LOG_FILE = Paths.get(System.getProperty("user.home"), "_how_long.log");

    public static void log(String message, Object... args) {
        var m = resolveLogMessage(message, args);
        logToConsole(m);
        m = applyDatePrefix(m);
        logToFile(m);
    }

    private static String resolveLogMessage(String message, Object... args) {
        var m = message;
        for (Object a : args) {
            m = m.replaceFirst("\\{}", a.toString());
        }
        return m;
    }

    private static void logToFile(String message) {
        try (var pw = new PrintWriter(new FileWriter(LOG_FILE.toString(), true))) {
            pw.println(message);
        } catch (IOException e) {
            System.out.println("ERROR: Could not log to a file.");
            e.printStackTrace();
        }
    }

    private static void logToConsole(String message) {
        System.out.println(message);
    }

    private static String applyDatePrefix(String m) {
        return String.format("%s %s", getCurrentDate(), m);
    }

    private static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return LocalDateTime.now().format(formatter);
    }
}
