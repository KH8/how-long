package com.h8.howlong.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LS = System.lineSeparator();

    private static String logFileName = "_HowLongLog.log";


    public static void log(String message, Object... args) {
        var m = setUpLogMessage(message, args);
        try {
            var logFile = new File(System.getProperty("user.home") + "\\" + logFileName);
            var fw = new FileWriter(logFile, true);
            var pw = new PrintWriter(fw, true);
            System.out.println(m);
            pw.println(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String setUpLogMessage(String message, Object... args) {
        var sb = new StringBuilder();
        sb.append(formatCurrentLocalDateTime()).append(LS).append(message).append(LS);
        for (Object a : args) {
            sb.append(a.toString()).append('/').append(LS);
        }
        return sb.toString();
    }

    private static String formatCurrentLocalDateTime() {
        var now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        return now.format(formatter);
    }

    public static void setLogFileName(String logFileName) {
        Logger.logFileName = logFileName;
    }
}
