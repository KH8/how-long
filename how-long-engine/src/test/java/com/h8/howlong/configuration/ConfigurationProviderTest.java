package com.h8.howlong.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationProviderTest {

    @Test
    void shouldResolvePlaceholdersProperly() {
        ConfigurationProvider provider = new ConfigurationProvider();
        assertThat(provider.getProperty("db.file.name"))
                .isEqualTo(System.getProperty("user.home") + "/_db");
    }

}