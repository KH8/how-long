package com.h8.howlong.admin.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.services.TimesheetContextService;

import java.time.LocalDateTime;

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
        switch (updateMode) {
            case FULL:
                return fullUpdate(ar.getMonth(), ar.getDay(), ar.getStartTime(), ar.getEndTime());
            case START_DATE:
                return startDateUpdate(ar.getMonth(), ar.getDay(), ar.getStartTime());
            case END_DATE:
                return endDateUpdate(ar.getMonth(), ar.getDay(), ar.getEndTime());
            default:
                return "Update has failed";
        }
    }

    private String startDateUpdate(int month, int day, LocalDateTime startTime) {
        var workday = service.getWorkDayOf(month, day).get();
        workday.setStart(startTime);
        service.updateWorkDay(workday);
        if (service.updateWorkDay(workday) != null) {
            return "The startTime of the day provided has been updated";
        } else return "The update failed";
    }

    private String endDateUpdate(int month, int day, LocalDateTime endTime) {
        var workday = service.getWorkDayOf(month, day).get();
        workday.setEnd(endTime);
        service.updateWorkDay(workday);
        if (service.updateWorkDay(workday) != null) {
            return "The endTime of the day provided has been updated";
        } else return "The update failed";
    }

    private String fullUpdate(int month, int day, LocalDateTime startTime, LocalDateTime endTime) {
        var workday = service.getWorkDayOf(month, day).get();
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
        } else if ("START_DATE".equals(arg)) {
            return UpdateMode.START_DATE;
        } else if ("END_DATE".equals(arg)) {
            return UpdateMode.END_DATE;
        } else
            throw new IllegalArgumentException("FULL, START_DATE or END_DATE command expected");
    }


    public enum UpdateMode {
        FULL,
        START_DATE,
        END_DATE
    }

}
