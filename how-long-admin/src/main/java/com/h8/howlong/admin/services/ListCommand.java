package com.h8.howlong.admin.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import com.h8.howlong.utils.print.PrintBuilder;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Singleton
public class ListCommand {

    private final TimesheetContextService service;

    @Inject
    public ListCommand(TimesheetContextService service) {
        this.service = service;
    }

    public String list(int month) {
        List<WorkDay> timesheet = service.getTimesheetForMonth(month);
        PrintBuilder b = PrintBuilder.builder();
        b.ln(String.format("Timesheet for: <c2018/%02d>", month));
        timesheet.forEach(w ->
                b.ln(String.format("<c%02d> | %s | %s | %s",
                        w.getStart().getDayOfMonth(),
                        w.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        w.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        DurationUtils.format(Duration.between(w.getStart(), w.getEnd())))
                )
        );
        return b.build();
    }

}
