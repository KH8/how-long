package com.h8.howlong.app.printers;

import com.h8.howlong.app.printers.print.PrintTable;
import com.h8.howlong.app.utils.DurationUtils;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

class ListPrintingService extends SummaryPrintingService {

    private final static Integer LIST_CELL_WIDTH = 18;

    private final static DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    ListPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    String buildSummary(List<WorkDay> timesheet) {
        PrintTable t = PrintTable.builder()
                .withCellWidth(LIST_CELL_WIDTH);
        t.addRow(Arrays.asList("<cDAY>", "<cSTART>", "<cEND>", "<cTOTAL>"));
        addWorkdays(t, timesheet);
        return t.serialize();
    }

    private void addWorkdays(PrintTable t, List<WorkDay> timesheet) {
        timesheet.forEach(d -> addWorkdays(t, d));
    }

    private void addWorkdays(PrintTable t, WorkDay workDay) {
        LocalDateTime s = workDay.getStart();
        LocalDateTime e = workDay.getEnd();
        t.addRow(Arrays.asList(
                String.format("#%2s | <c%s>", s.getDayOfMonth(), s.getDayOfWeek()),
                String.format("<y%s>", s.toLocalTime().format(LOCAL_TIME_FORMATTER)),
                String.format("<y%s>", e.toLocalTime().format(LOCAL_TIME_FORMATTER)),
                String.format("<y%s>", DurationUtils.format(getElapsedTime(workDay)))));
    }

}
