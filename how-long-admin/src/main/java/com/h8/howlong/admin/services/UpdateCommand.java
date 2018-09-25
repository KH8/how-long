package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.services.TimesheetContextService;

import java.time.LocalTime;

@Singleton
public class UpdateCommand {

    private final TimesheetContextService service;

    @Inject
    public UpdateCommand(TimesheetContextService service) {
        this.service = service;
    }

    public String update(int month, int day, UpdateMode mode, LocalTime... times) {
        var workDay = service.getWorkDayOf(month, day);
        return "done";
    }

    public static enum UpdateMode {
        FULL,
        START_DATE,
        END_DATE
    }

}
