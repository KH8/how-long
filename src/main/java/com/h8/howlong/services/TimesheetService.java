package com.h8.howlong.services;

import com.h8.howlong.domain.WorkDay;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimesheetService {

    private TimesheetContextService service;

    public TimesheetService(TimesheetContextService service) {
        this.service = service;
    }

    public WorkDay updateWorkDay() {
        WorkDay wd = getWorkDayOfToday();
        wd.setEnd(LocalDateTime.now());
        return service.updateWorkDay(wd);
    }

    public Duration getElapsedTime() {
        WorkDay wd = getWorkDayOfToday();
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

    public Duration getRemainingTime() {
        return Duration.ofHours(8).minus(getElapsedTime());
    }

    private WorkDay getWorkDayOfToday() {
        return service.getWorkDayOfToday();
    }

}
