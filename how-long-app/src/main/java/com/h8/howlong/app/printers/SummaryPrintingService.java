package com.h8.howlong.app.printers;

import com.h8.howlong.app.printers.print.PrintBuilder;
import com.h8.howlong.app.utils.DurationUtils;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public abstract class SummaryPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    SummaryPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        return PrintBuilder.builder()
                .ln(String.format("<c%s %s>", Month.of(month), LocalDate.now().getYear())).ln()
                .ln(buildSummary(contextService.getTimesheetForMonth(month))).ln()
                .ln(String.format("Total: <y%s> (days: <y%s>)",
                        DurationUtils.format(contextService.getTotalWorkingTime(month)),
                        contextService.getTotalWorkingDayCount(month)))
                .ln(String.format("Average: <y%s>",
                        DurationUtils.format(contextService.getAverageWorkingTime(month))))
                .build();
    }

    abstract String buildSummary(List<WorkDay> timesheet);

    Duration getElapsedTime(WorkDay wd) {
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

}
