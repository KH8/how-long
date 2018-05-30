package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.printers.print.PrintBuilder;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class DefaultPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    DefaultPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        WorkDay wd = contextService.getWorkDayOfToday();
        return PrintBuilder.builder()
                .ln(String.format("Today is <c%s>", wd.getStart().toLocalDate()))
                .ln(String.format("- started at: <y%s>", wd.getStart().toLocalTime()
                        .format(DateTimeFormatter.ofPattern("hh:mm:ss"))))
                .ln(String.format("- elapsed time: <y%s>", DurationUtils.format(getElapsedTime(wd))))
                .ln(String.format("- remaining time: <y%s>", DurationUtils.format(getRemainingTime(wd))))
                .ln("<cEnjoy the day!>")
                .build();
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
