package com.h8.howlong.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HowLongLogger {

    private static Logger logger;
    private static FileHandler fh;

    public static void log(String message, Object... args) {
        var m = message;
        for (Object a : args) {
            m = m.replaceFirst("\\{}", a.toString());
        }
        setUpLogger();
        logger.info(m);
        fh.close();
    }

    private static void setUpLogger() {
        logger = Logger.getLogger("MyLog");
        try {
            var path = System.getProperty("user.home") + File.separator + "_HowLongLog.log";
            fh = new FileHandler(path, true);
            logger.addHandler(fh);
            var formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
