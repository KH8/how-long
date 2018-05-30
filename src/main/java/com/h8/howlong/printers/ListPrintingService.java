package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.printers.print.PrintTable;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;

import java.time.*;
import java.util.Arrays;
import java.util.List;

class ListPrintingService extends SummaryPrintingService {

    private final static Integer LIST_CELL_WIDTH = 18;

    ListPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    PrintTable buildSummary(List<WorkDay> timesheet) {
        PrintTable t = PrintTable.builder()
                .withCellWidth(LIST_CELL_WIDTH);
        t.addRow(Arrays.asList("<cDAY>", "<cSTART>", "<cEND>", "<cTOTAL>"));
        addWorkdays(t, timesheet);
        return t;
    }

    private void addWorkdays(PrintTable t, List<WorkDay> timesheet) {
        timesheet.forEach(d -> addWorkdays(t, d));
    }

    private void addWorkdays(PrintTable t, WorkDay workDay) {
        LocalDateTime s = workDay.getStart();
        LocalDateTime e = workDay.getEnd();
        t.addRow(Arrays.asList(
                String.format("#%s | <c%s>", s.getDayOfMonth(), s.getDayOfWeek()),
                String.format("<y%s>", printLocalTime(s.toLocalTime())),
                String.format("<y%s>", printLocalTime(e.toLocalTime())),
                String.format("<y%s>", DurationUtils.format(getElapsedTime(workDay)))));
    }

}
