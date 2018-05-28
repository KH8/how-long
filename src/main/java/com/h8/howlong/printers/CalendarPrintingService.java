package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import net.steppschuh.markdowngenerator.table.Table;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalendarPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    CalendarPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        return "" +
                LS + buildTable(contextService.getTimesheetForMonth(month)).serialize() + LS +
                LS + "Total: " + DurationUtils.format(contextService.getTotalWorkingTime(month)) +
                LS + "Average: " + DurationUtils.format(contextService.getAverageWorkingTime(month));
    }

    private Table buildTable(List<WorkDay> timesheet) {
        Table.Builder builder = new Table.Builder();
        addHeaderRow(builder);
        addWorkdays(builder, timesheet.iterator());
        Table table = builder.build();
        table.setMinimumColumnWidth(10);
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
        return String.format(" #%02d %s ",
                workDay.getStart().getDayOfMonth(),
                DurationUtils.format(getElapsedTime(workDay)));
    }

    private Duration getElapsedTime(WorkDay wd) {
        return Duration.between(
                wd.getStart(),
                wd.getEnd());
    }

}
