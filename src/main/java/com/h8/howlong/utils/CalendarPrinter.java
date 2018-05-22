package com.h8.howlong.utils;

import com.h8.howlong.domain.WorkDay;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

public class CalendarPrinter {

    public String printMonth(int month, Collection<WorkDay> workDays) {
        Iterator<WorkDay> i = createMonthIterator(month, workDays);
        StringBuilder sb = new StringBuilder();
        sb = printHeader(sb);
        sb = printNewLine(sb);
        sb = printWorkDays(sb, i);
        return printNewLine(sb).toString();
    }

    private Iterator<WorkDay> createMonthIterator(int month, Collection<WorkDay> workDays)  {
        LocalDate today = LocalDate.now();
        return workDays
                .stream()
                .filter(d -> today.getYear() == d.getStart().getYear())
                .filter(d -> today.getMonthValue() == month)
                .sorted(Comparator.comparing(WorkDay::getStart))
                .iterator();
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
        return sb;
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
