package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import net.steppschuh.markdowngenerator.table.Table;

import java.time.*;
import java.util.List;

class ListPrintingService extends SummaryPrintingService {

    ListPrintingService(TimesheetContextService contextService) {
        super(contextService);
    }

    @Override
    Table buildSummary(List<WorkDay> timesheet) {
        Table.Builder builder = new Table.Builder()
                .addRow("DAY", "START", "END", "TOTAL");
        addWorkdays(builder, timesheet);
        return builder.build();
    }

    private void addWorkdays(Table.Builder builder, List<WorkDay> timesheet) {
        timesheet.forEach(d -> addWorkdays(builder, d));
    }

    private void addWorkdays(Table.Builder builder, WorkDay workDay) {
        LocalDateTime s = workDay.getStart();
        LocalDateTime e = workDay.getEnd();
        builder.addRow(s.getDayOfMonth() + "/" + s.getMonthValue() + "/" + s.getDayOfWeek(),
                printLocalTime(s.toLocalTime()),
                printLocalTime(e.toLocalTime()),
                DurationUtils.format(getElapsedTime(workDay)));
    }

}
