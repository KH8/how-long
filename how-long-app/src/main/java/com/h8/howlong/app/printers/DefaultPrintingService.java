package com.h8.howlong.app.printers;

import com.h8.howlong.app.printers.print.PrintBuilder;
import com.h8.howlong.app.utils.DurationUtils;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.WorkDayComputationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    private final WorkDayComputationService computationService;

    DefaultPrintingService(
            TimesheetContextService contextService,
            WorkDayComputationService computationService) {
        this.contextService = contextService;
        this.computationService = computationService;
    }

    @Override
    public String print(int month) {
        WorkDay wd = contextService.getWorkDayOfToday();
        return PrintBuilder.builder()
                .ln(String.format("Today is <c%s>", LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))))
                .ln(String.format("- started at: <y%s>", wd.getStart().toLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .ln(String.format("- end suggested at: <y%s>", computationService.getSuggestedEndTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
                .ln(String.format("- elapsed time: <y%s>",
                        DurationUtils.format(computationService.getElapsedTime())))
                .ln(String.format("- remaining time: <y%s>",
                        DurationUtils.format(computationService.getRemainingTime())))
                .ln("<cEnjoy the day!>")
                .build();
    }

}
