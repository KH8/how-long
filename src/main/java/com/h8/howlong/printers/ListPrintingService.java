package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.DurationUtils;
import net.steppschuh.markdowngenerator.table.Table;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    ListPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        return LS + buildTable(contextService.getTimesheetForMonth(month)).serialize() +
                LS + LS + "Total: " + DurationUtils.format(contextService.getTotalWorkingTime(month)) +
                LS + "Average: " + DurationUtils.format(contextService.getAverageWorkingTime(month));
    }

    private Table buildTable(List<WorkDay> timesheet) {
        Table.Builder builder = new Table.Builder()
                .addRow("#day", "start", "end", "total");
        addWorkdays(builder, timesheet);
        return builder.build();
    }

    private void addWorkdays(Table.Builder builder, List<WorkDay> timesheet) {
        timesheet.forEach(d -> addWorkdays(builder, d));
    }

    private void addWorkdays(Table.Builder builder, WorkDay workDay) {
        Duration d = Duration.between(
                workDay.getStart().toLocalTime(),
                workDay.getEnd().toLocalTime());
        int dayOfMonth = workDay.getStart().getDayOfMonth();
        int month = workDay.getStart().getMonthValue();
        builder.addRow(dayOfMonth + "." + month,
                printLocalTime(workDay.getStart().toLocalTime()),
                printLocalTime(workDay.getEnd().toLocalTime()),
                DurationUtils.format(d));
    }

    private String printLocalTime(LocalTime localTime) {
        return DateTimeFormatter.ofPattern("hh:mm:ss").format(localTime);
    }

}
