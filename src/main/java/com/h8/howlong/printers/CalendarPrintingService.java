package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.utils.CalendarDayOfWeek;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;
import java.util.Iterator;
import java.util.stream.Stream;

public class CalendarPrintingService implements PrintingService {

    private final TimesheetContextService contextService;

    CalendarPrintingService(TimesheetContextService contextService) {
        this.contextService = contextService;
    }

    @Override
    public String print(int month) {
        return "-----------------------------------------------------------------------------------" +
                LS + print(contextService.getTimesheetForMonth(month).iterator()) +
                "-----------------------------------------------------------------------------------" +
                LS + "Total: " + DurationUtils.format(contextService.getTotalWorkingTime(month)) +
                LS + "Average: " + DurationUtils.format(contextService.getAverageWorkingTime(month));
    }

    private StringBuilder print(Iterator<WorkDay> i) {
        StringBuilder sb = new StringBuilder();
        sb = printHeader(sb);
        sb = printWorkDays(sb, i);
        return printNewLine(sb);
    }

    private StringBuilder printWorkDays(StringBuilder sb, Iterator<WorkDay> iterator) {
        if (iterator.hasNext()) {
            WorkDay c = iterator.next();
            sb = printWorkDays(sb, iterator, c);
        }
        return sb;
    }

    private StringBuilder printWorkDays(StringBuilder sb, Iterator<WorkDay> iterator, WorkDay first) {
        WorkDay c = first;
        for (CalendarDayOfWeek d : CalendarDayOfWeek.values()) {
            if (d.getDayOfWeek().equals(c.getStart().getDayOfWeek())) {
                sb = printWorkDay(sb, c);
                if (iterator.hasNext()) {
                    c = iterator.next();
                } else {
                    break;
                }
            } else {
                sb = printBlank(sb);
            }
            if (CalendarDayOfWeek.SUN.equals(d)) {
                sb = printNewLine(sb);
                sb = printWorkDays(sb, iterator, c);
            } else {
                sb = printSeparator(sb);
            }
        }
        return sb;
    }

    private StringBuilder printWorkDay(StringBuilder sb, WorkDay workDay) {
        Duration d = Duration.between(
                workDay.getStart().toLocalTime(),
                workDay.getEnd().toLocalTime());
        int dayOfMonth = workDay.getStart().getDayOfMonth();
        return sb.append(String.format(" #%02d %s ", dayOfMonth, DurationUtils.format(d)));
    }

    private StringBuilder printHeader(StringBuilder sb) {
        Iterator<CalendarDayOfWeek> iterator =
                Stream.of(CalendarDayOfWeek.values()).iterator();
        while (iterator.hasNext()) {
            sb = printElement(sb, " " + iterator.next().name() + " ");
            if (iterator.hasNext()) {
                sb = printSeparator(sb);
            }
        }
        return printNewLine(sb);
    }

    private StringBuilder printBlank(StringBuilder sb) {
        return sb.append("           ");
    }

    private StringBuilder printSeparator(StringBuilder sb) {
        return sb.append("|");
    }

    private StringBuilder printNewLine(StringBuilder sb) {
        return sb.append(System.lineSeparator());
    }

    private StringBuilder printElement(StringBuilder sb, String content) {
        return sb.append("   ").append(content).append("   ");
    }

}
