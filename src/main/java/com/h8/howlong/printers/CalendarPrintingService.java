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

    private final static Integer CALENDAR_CELL_WIDTH = 14;

    CalendarPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    PrintTable buildSummary(List<WorkDay> timesheet) {
        PrintTable t = PrintTable.builder()
                .withCellWidth(CALENDAR_CELL_WIDTH);
        addHeaderRow(t);
        addWorkdays(t, timesheet.iterator());
        return t;
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

        List<String> week = new ArrayList<>();
        for (DayOfWeek d : DayOfWeek.values()) {
            if (d.equals(c.getStart().getDayOfWeek())) {
                String day = printWorkDay(c);
                week.add(day);
                if (timesheet.hasNext()) {
                    c = timesheet.next();
                    if (d.compareTo(c.getStart().getDayOfWeek()) > 0) {
                        repeat = true;
                    }
                }
            } else {
                week.add("<c >");
            }
        }

        t.addRow(week);
        if (repeat) {
            addWorkdays(t, timesheet, c);
        }
    }

    private String printWorkDay(WorkDay workDay) {
        return String.format("#%02d - <y%s>",
                workDay.getStart().getDayOfMonth(),
                DurationUtils.format(getElapsedTime(workDay)));
    }

}
