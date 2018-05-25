package com.h8.howlong.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationProvider {

    private final static String PROPERTIES_FILE_NAME = "com/h8/howlong/howlong.properties";

    private final static String PLACEHOLDER_REGEX = "\\{(.*?)}";

    private final Properties properties;

    public ConfigurationProvider() {
        try {
            this.properties = loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Configuration could not be initialized");
        }
    }

    private Properties loadProperties()
            throws IOException {
        InputStream stream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }

    public String getProperty(String key) {
        String p = properties.getProperty(key);
        p = replacePlaceholders(p);
        return p;
    }

    private String replacePlaceholders(String s) {
        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            String key = m.group(1);
            String value = resolveSystemProperty(key);
            s = s.replace("{" + key + "}", value);
        }
        return s;
    }

    private String resolveSystemProperty(String key) {
        return System.getProperty(key);
    }

}
