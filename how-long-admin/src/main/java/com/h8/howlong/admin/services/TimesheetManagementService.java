package com.h8.howlong.admin.services;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public void updateStartTime(Integer month, Integer day, LocalTime localTime)
            throws TimesheetManagementFailedException {
        var startTime = toLocalDateTime(month, day, localTime);

        WorkDay workday = contextService.getWorkDayOf(month, day)
                .orElse(contextService.createWorkDayOfGivenDate(startTime, startTime));

        if (workday.getEnd().isBefore(startTime)) {
            var message = String.format("Provided end time '%s' is before start time '%s' of the given day",
                    startTime, workday.getEnd());
            throw new TimesheetManagementFailedException(message);
        }

        workday.setStart(startTime);
        contextService.updateWorkDay(workday);
    }

    public void updateEndTime(Integer month, Integer day, LocalTime localTime)
            throws TimesheetManagementFailedException {
        var endTime = toLocalDateTime(month, day, localTime);

        WorkDay workday = contextService.getWorkDayOf(month, day)
                .orElse(contextService.createWorkDayOfGivenDate(endTime, endTime));

        if (workday.getStart().isAfter(endTime)) {
            var message = String.format("Provided start time '%s' is after end time '%s' of the given day",
                    workday.getStart(), endTime);
            throw new TimesheetManagementFailedException(message);
        }

        workday.setEnd(endTime);
        contextService.updateWorkDay(workday);
    }

    public void delete(Integer month, Integer day)
            throws TimesheetManagementFailedException {
        var deleted = contextService.deleteWorkday(month, day);
        if (!deleted) {
            throw new TimesheetManagementFailedException("Provided day could not be deleted");
        }
    }

    private LocalDateTime toLocalDateTime(Integer month, Integer day, LocalTime localTime) {
        var localDate = LocalDate.of(getCurrentYear(), month, day);
        return LocalDateTime.of(localDate, localTime);
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

}
