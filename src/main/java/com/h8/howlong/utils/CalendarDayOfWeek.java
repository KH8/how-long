package com.h8.howlong.utils;

import java.time.DayOfWeek;

public enum CalendarDayOfWeek {
    MON(DayOfWeek.MONDAY),
    TUE(DayOfWeek.TUESDAY),
    WED(DayOfWeek.WEDNESDAY),
    THU(DayOfWeek.THURSDAY),
    FRI(DayOfWeek.FRIDAY),
    SAT(DayOfWeek.SATURDAY),
    SUN(DayOfWeek.SUNDAY);

    private final DayOfWeek dayOfWeek;

    CalendarDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

}
