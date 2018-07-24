package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.printers.print.PrintTable;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class CalendarPrintingService extends SummaryPrintingService {

    private final static Integer CALENDAR_CELL_WIDTH = 12;

    CalendarPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    String buildSummary(List<WorkDay> timesheet) {
        PrintTable t = PrintTable.builder()
                .withCellWidth(CALENDAR_CELL_WIDTH);
        addHeaderRow(t);
        addWorkdays(t, timesheet.iterator());
        return t.serialize();
    }

    private void addHeaderRow(PrintTable t) {
        List<String> headers = Arrays.stream(DayOfWeek.values())
                .map(d -> String.format("<c%s>", d))
                .collect(Collectors.toList());
        t.addRow(headers);
    }

    private void addWorkdays(PrintTable t, Iterator<WorkDay> timesheet) {
        if (timesheet.hasNext()) {
            addWorkdays(t, timesheet, timesheet.next());
        }
    }

    private void addWorkdays(PrintTable t, Iterator<WorkDay> timesheet, WorkDay next) {
        WorkDay c = next;
        boolean repeat = false;

        List<WorkDay> week = new ArrayList<>();
        for (DayOfWeek d : DayOfWeek.values()) {
            if (d.equals(c.getStart().getDayOfWeek())) {
                week.add(c);
                if (timesheet.hasNext()) {
                    c = timesheet.next();
                    if (d.compareTo(c.getStart().getDayOfWeek()) > 0) {
                        repeat = true;
                    }
                }
            } else {
                week.add(null);
            }
        }

        addWorkdays(t, week);
        if (repeat) {
            addWorkdays(t, timesheet, c);
        }
    }

    private void addWorkdays(PrintTable t, List<WorkDay> week) {
        List<String> row = week
                .stream()
                .map(this::printWorkDay)
                .collect(Collectors.toList());
        t.addRow(row);
    }

    private String printWorkDay(WorkDay d) {
        return d != null ?
                String.format("#%2d <y%s>",
                        d.getStart().getDayOfMonth(),
                        DurationUtils.format(getElapsedTime(d))) :
                "<c >";
    }

}
