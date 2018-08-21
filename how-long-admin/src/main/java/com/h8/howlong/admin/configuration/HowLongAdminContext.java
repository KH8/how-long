package com.h8.howlong.admin.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.ListCommand;
import lombok.Getter;

@Getter
@Singleton
public class HowLongAdminContext {

    private final ListCommand listCommand;

    @Inject
    public HowLongAdminContext(
            ListCommand listCommand) {
        this.listCommand = listCommand;
    }

}