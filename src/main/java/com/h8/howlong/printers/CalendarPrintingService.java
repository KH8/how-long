package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import net.steppschuh.markdowngenerator.table.Table;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CalendarPrintingService extends SummaryPrintingService {

    private final static Integer MINIMAL_CALENDAR_CELL_WIDTH = 9;

    CalendarPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    Table buildSummary(List<WorkDay> timesheet) {
        Table.Builder builder = new Table.Builder();
        addHeaderRow(builder);
        addWorkdays(builder, timesheet.iterator());
        Table table = builder.build();
        table.setMinimumColumnWidth(MINIMAL_CALENDAR_CELL_WIDTH);
        return table;
    }

    private void addHeaderRow(Table.Builder builder) {
        builder.addRow((Object[]) DayOfWeek.values());
    }

    private void addWorkdays(Table.Builder builder, Iterator<WorkDay> timesheet) {
        if (timesheet.hasNext()) {
            addWorkdays(builder, timesheet, timesheet.next());
        }
    }

    private void addWorkdays(Table.Builder builder, Iterator<WorkDay> timesheet, WorkDay next) {
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
                week.add("");
            }
        }

        builder.addRow(week.toArray());
        if (repeat) {
            addWorkdays(builder, timesheet, c);
        }
    }

    private String printWorkDay(WorkDay workDay) {
        return String.format("#%02d %s",
                workDay.getStart().getDayOfMonth(),
                DurationUtils.format(getElapsedTime(workDay)));
    }

}
