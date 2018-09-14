package com.h8.howlong.admin.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Singleton
public class UpdateCommandAdministrationService implements AdministrationService {

    private final TimesheetContextService service;

    @Inject
    public UpdateCommandAdministrationService(TimesheetContextService service) {
        this.service = service;
    }

    @Override
    public String modifyTimesheet(ArgumentResolver ar) {
        var updateMode = getUpdateMode(ar.getUpdateMode());
        var workday = getWorkdayOrInitializeIfNotExists(ar.getMonth(), ar.getDay(), ar.getStartTime(), ar.getEndTime());
        switch (updateMode) {
            case FULL:
                return fullUpdate(workday, ar.getStartTime(), ar.getEndTime());
            case START_DATE:
                return startDateUpdate(workday, ar.getStartTime());
            case END_DATE:
                return endDateUpdate(workday, ar.getEndTime());
            default:
                return "Update has failed";
        }
    }

    private String startDateUpdate(WorkDay workday, LocalDateTime startTime) {
        workday.setStart(startTime);
        service.updateWorkDay(workday);
        if (service.updateWorkDay(workday) != null) {
            return "The startTime of the day provided has been updated";
        } else return "The update failed";
    }

    private String endDateUpdate(WorkDay workday, LocalDateTime endTime) {
        workday.setEnd(endTime);
        service.updateWorkDay(workday);
        if (service.updateWorkDay(workday) != null) {
            return "The endTime of the day provided has been updated";
        } else return "The update failed";
    }

    private String fullUpdate(WorkDay workday, LocalDateTime startTime, LocalDateTime endTime) {
        workday.setStart(startTime);
        workday.setEnd(endTime);
        service.updateWorkDay(workday);
        if (service.updateWorkDay(workday) != null) {
            return "The startTime and the endTime of the day provided has been updated";
        } else return "The update failed";
    }

    private UpdateMode getUpdateMode(String arg) {

        if ("FULL".equals(arg)) {
            return UpdateMode.FULL;
        } else if ("START".equals(arg)) {
            return UpdateMode.START_DATE;
        } else if ("END".equals(arg)) {
            return UpdateMode.END_DATE;
        } else
            throw new IllegalArgumentException("FULL, START_DATE or END_DATE command expected");
    }

    private WorkDay getWorkdayOrInitializeIfNotExists(int month, int day, LocalDateTime start, LocalDateTime end) {
        if (start == null) start = initializeStart(month, day);
        if (end == null) end = initializeEnd(month, day);
        return service.getWorkDayOf(month, day).isPresent() ? service.getWorkDayOf(month, day).get() : service.createWorkDayOfGivenDate(start, end);
    }

    private LocalDateTime initializeStart(int month, int day) {
        return LocalDateTime.of(LocalDate.of(LocalDate.now().getYear(), month, day), LocalTime.of(0, 0, 1));
    }

    private LocalDateTime initializeEnd(int month, int day) {
        return LocalDateTime.of(LocalDate.of(LocalDate.now().getYear(), month, day), LocalTime.of(23, 59, 59));
    }


    public enum UpdateMode {
        FULL,
        START_DATE,
        END_DATE
    }

}
