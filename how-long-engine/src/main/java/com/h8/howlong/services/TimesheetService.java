package com.h8.howlong.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.domain.WorkDay;

import java.time.LocalDateTime;

@Singleton
public class TimesheetService {

    private TimesheetContextService service;

    @Inject
    public TimesheetService(TimesheetContextService service) {
        this.service = service;
    }

    public WorkDay updateWorkDayEndTime() {
        WorkDay wd = service.getWorkDayOfToday();
        wd.setEnd(LocalDateTime.now());
        return service.updateWorkDay(wd);
    }

}