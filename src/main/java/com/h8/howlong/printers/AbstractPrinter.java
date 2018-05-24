package com.h8.howlong.printers;

import com.h8.howlong.domain.WorkDay;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public abstract class AbstractPrinter {

    public String printMonth(int month, Collection<WorkDay> workDays) {
        Iterator<WorkDay> i = createMonthIterator(month, workDays);
        return print(new StringBuilder(), i).toString();
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

    protected abstract StringBuilder print(StringBuilder sb, Iterator<WorkDay> i);

}
