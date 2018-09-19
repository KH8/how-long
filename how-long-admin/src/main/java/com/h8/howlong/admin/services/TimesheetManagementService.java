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
        //TODO!!!
    }

    public void updateEndTime(Integer month, Integer day, LocalDateTime time)
            throws TimesheetManagementFailedException {
        //TODO!!!
    }

    public void delete(Integer month, Integer day)
            throws TimesheetManagementFailedException {
        //TODO!!!
    }

}
