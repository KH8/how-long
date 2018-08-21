package com.h8.howlong.configuration;

import com.google.inject.Singleton;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

@Singleton
public class ConfigurationProvider {

    private final static String PROPERTIES_FILE_NAME = "how-long.properties";

    private final static String PLACEHOLDER_REGEX = "\\{(.*?)}";

    private final Properties properties;

    ConfigurationProvider() {
        try {
            this.properties = loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Configuration could not be initialized");
        }
    }

    private Properties loadProperties()
            throws IOException {
        var stream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        var properties = new Properties();
        properties.load(stream);
        return properties;
    }

    public String getProperty(String key) {
        var p = properties.getProperty(key);
        p = replacePlaceholders(p);
        return p;
    }

    private String replacePlaceholders(String s) {
        var pattern = Pattern.compile(PLACEHOLDER_REGEX);
        var m = pattern.matcher(s);
        while (m.find()) {
            var key = m.group(1);
            var value = resolveSystemProperty(key);
            s = s.replace("{" + key + "}", value);
        }
        return s;
    }

    private String resolveSystemProperty(String key) {
        return System.getProperty(key);
    }

}
