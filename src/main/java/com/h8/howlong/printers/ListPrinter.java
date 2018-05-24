package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class ListPrinter extends AbstractPrinter {

    @Override
    protected StringBuilder print(StringBuilder sb, Iterator<WorkDay> i) {
        sb = printHeader(sb);
        sb = printHeaderSeparator(sb);
        return printWorkDays(sb, i);
    }

    private StringBuilder printWorkDays(StringBuilder sb, Iterator<WorkDay> iterator) {
        while (iterator.hasNext()) {
            WorkDay c = iterator.next();
            sb = printWorkDay(sb, c);
            sb = printNewLine(sb);
        }
        return sb;
    }

    private StringBuilder printWorkDay(StringBuilder sb, WorkDay workDay) {
        Duration d = Duration.between(
                workDay.getStart().toLocalTime(),
                workDay.getEnd().toLocalTime());
        int dayOfMonth = workDay.getStart().getDayOfMonth();
        int month = workDay.getStart().getMonthValue();
        return sb.append(String.format(" #%02d.%02d | %s | %s | %s ",
                dayOfMonth, month,
                printLocalTime(workDay.getStart().toLocalTime()),
                printLocalTime(workDay.getEnd().toLocalTime()),
                DurationUtils.format(d)));
    }

    private StringBuilder printHeader(StringBuilder sb) {
        sb = sb.append(" #day   | start    | end      | total ");
        return printNewLine(sb);
    }

    private StringBuilder printHeaderSeparator(StringBuilder sb) {
        sb = sb.append("-------------------------------------");
        return printNewLine(sb);
    }

    private StringBuilder printNewLine(StringBuilder sb) {
        return sb.append(System.lineSeparator());
    }

    private String printLocalTime(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        return formatter.format(localTime);
    }

}
