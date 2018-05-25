package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;

public class DefaultPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    DefaultPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        WorkDay wd = contextService.getWorkDayOfToday();
        return ("Today is " + wd.getStart().toLocalDate()) +
                LS + "- started at: " + wd.getStart().toLocalTime() +
                LS + "- elapsed time: " + DurationUtils.format(getElapsedTime(wd)) +
                LS +  "- remaining time: " + DurationUtils.format(getRemainingTime(wd)) +
                LS + "Enjoy the day!";
    }

    private Duration getElapsedTime(WorkDay wd) {
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

    private Duration getRemainingTime(WorkDay wd) {
        return Duration.ofHours(8).minus(getElapsedTime(wd));
    }

}
