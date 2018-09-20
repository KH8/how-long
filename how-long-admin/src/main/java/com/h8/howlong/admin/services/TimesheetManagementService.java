package com.h8.howlong.admin.services;

import com.h8.howlong.domain.*;
import com.h8.howlong.services.*;

import javax.inject.*;
import java.time.*;
import java.util.*;

public class TimesheetManagementService {

    private TimesheetContextService contextService;

    @Inject
    public TimesheetManagementService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    public List<WorkDay> getTimesheet(Integer month) {
        return contextService.getTimesheetForMonth(month);
    }

    public void updateStartTime(Integer month, Integer day, LocalDateTime time)
            throws TimesheetManagementFailedException {

        WorkDay workday = contextService.getWorkDayOf(month, day)
                .orElse(contextService.createWorkDayOfGivenDate(time, time));
        if (workday.getEnd().isAfter(time)) {
            workday.setStart(time);
        } else throw new TimesheetManagementFailedException("Provided start time is after end time of the given day");
        contextService.updateWorkDay(workday);
        if (contextService.updateWorkDay(workday) == null)
            throw new TimesheetManagementFailedException("Start date of provided day has not been updated");
    }

    public void updateEndTime(Integer month, Integer day, LocalDateTime time)
            throws TimesheetManagementFailedException {
        WorkDay workday = contextService.getWorkDayOf(month, day)
                .orElse(contextService.createWorkDayOfGivenDate(time,time));
        if (workday.getStart().isBefore(time)) {
            workday.setStart(time);
        } else throw new TimesheetManagementFailedException("Provided start time is after end time of the given day");
        workday.setEnd(time);
        contextService.updateWorkDay(workday);
        if (contextService.updateWorkDay(workday) == null)
            throw new TimesheetManagementFailedException("End date of provided day has not been updated");
    }

    public void delete(Integer month, Integer day)
            throws TimesheetManagementFailedException {
        if (contextService.deleteWorkday(month, day)) {
            contextService.deleteWorkday(month, day);
        } else throw new TimesheetManagementFailedException("Provided day has not been found");
    }
}
