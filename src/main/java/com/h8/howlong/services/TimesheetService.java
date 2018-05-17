package com.h8.howlong.services;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.domain.WorkTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimesheetService {

    private TimesheetContextService service;

    public TimesheetService() {
        this.service = new TimesheetContextService();
    }

    public WorkDay updateWorkDay() {
        WorkDay wd = getWorkDayOfToday();
        wd.setEnd(WorkTimestamp.builder()
                .timestamp(LocalDateTime.now())
                .build());
        service.updateWorkDay(wd);
        return wd;
    }

    public Duration getElapsedTime() {
        WorkDay wd = getWorkDayOfToday();
        return Duration.between(
                wd.getStart().getTimestamp(),
                wd.getEnd().getTimestamp());
    }

    public Duration getRemainingTime() {
        return Duration.ofHours(8).minus(getElapsedTime());
    }

    private WorkDay getWorkDayOfToday() {
        return service.getWorkDayOfToday();
    }

}
