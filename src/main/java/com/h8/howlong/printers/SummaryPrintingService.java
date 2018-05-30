package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.printers.print.PrintBuilder;
import com.h8.howlong.printers.print.PrintTable;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
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
                .ln(buildSummary(contextService.getTimesheetForMonth(month)).serialize()).ln()
                .ln(String.format("Total: <y%s>", DurationUtils.format(contextService.getTotalWorkingTime(month))))
                .ln(String.format("Average: <y%s>", DurationUtils.format(contextService.getAverageWorkingTime(month))))
                .build();
    }

    abstract PrintTable buildSummary(List<WorkDay> timesheet);

    Duration getElapsedTime(WorkDay wd) {
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

    String printLocalTime(LocalTime localTime) {
        return DateTimeFormatter.ofPattern("hh:mm:ss").format(localTime);
    }

}
