package com.h8.howlong.admin.services;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

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
        if (workday.getEnd().isBefore(time)) {
            throw new TimesheetManagementFailedException("Provided start time is after end time of the given day");
        } else workday.setStart(time);
        var updated = contextService.updateWorkDay(workday);
        if (updated == null)
            throw new TimesheetManagementFailedException("Start date of provided day has not been updated");
    }

    public void updateEndTime(Integer month, Integer day, LocalDateTime time)
            throws TimesheetManagementFailedException {
        WorkDay workday = contextService.getWorkDayOf(month, day)
                .orElse(contextService.createWorkDayOfGivenDate(time, time));
        if (workday.getStart().isAfter(time)) {
            throw new TimesheetManagementFailedException("Provided start time is after end time of the given day");
        } else workday.setEnd(time);
        var updated = contextService.updateWorkDay(workday);
        if (updated == null)
            throw new TimesheetManagementFailedException("End date of provided day has not been updated");
    }

    public void delete(Integer month, Integer day)
            throws TimesheetManagementFailedException {
        var deleted = contextService.deleteWorkday(month, day);
        if (!deleted) {
            throw new TimesheetManagementFailedException("Provided day has not been found");
        }
    }
}
