package com.h8.howlong.admin.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.h8.howlong.admin.services.AdministrationService;
import com.h8.howlong.admin.utils.ArgumentResolver;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import com.h8.howlong.utils.print.PrintBuilder;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Singleton
public class ListCommandAdministrationService implements AdministrationService {

    private final TimesheetContextService service;

    @Inject
    public ListCommandAdministrationService(TimesheetContextService service) {
        this.service = service;
    }

    @Override
    public String modifyTimesheet(ArgumentResolver ar) {
        return list(ar.getMonth());
    }


    private String list(int month) {
        var timesheet = service.getTimesheetForMonth(month);
        var b = PrintBuilder.builder();
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
