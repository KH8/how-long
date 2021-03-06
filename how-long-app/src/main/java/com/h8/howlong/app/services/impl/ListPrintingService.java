package com.h8.howlong.app.services.impl;

import com.h8.howlong.utils.DurationUtils;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.print.PrintTable;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ListPrintingService extends SummaryPrintingService {

    private final static Integer LIST_CELL_WIDTH = 18;

    private final static DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ListPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    String buildSummary(List<WorkDay> timesheet) {
        var t = PrintTable.builder()
                .withCellWidth(LIST_CELL_WIDTH);
        t.addRow(Arrays.asList("<cDAY>", "<cSTART>", "<cEND>", "<cTOTAL>"));
        addWorkdays(t, timesheet);
        return t.serialize();
    }

    private void addWorkdays(PrintTable t, List<WorkDay> timesheet) {
        timesheet.forEach(d -> addWorkdays(t, d));
    }

    private void addWorkdays(PrintTable t, WorkDay workDay) {
        var s = workDay.getStart();
        var e = workDay.getEnd();
        t.addRow(Arrays.asList(
                String.format("#%2s | <c%s>", s.getDayOfMonth(), s.getDayOfWeek()),
                String.format("<y%s>", s.toLocalTime().format(LOCAL_TIME_FORMATTER)),
                String.format("<y%s>", e.toLocalTime().format(LOCAL_TIME_FORMATTER)),
                String.format("<y%s>", DurationUtils.format(getElapsedTime(workDay)))));
    }

}
