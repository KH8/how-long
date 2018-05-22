package com.h8.howlong.utils;

import com.h8.howlong.domain.WorkDay;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class CalendarPrinterTest {

    @Test
    void shouldPrintGivenDays() {
        LocalDateTime timeBaseline = LocalDate.now().atStartOfDay();
        List<WorkDay> weekDays = Arrays.asList(
                WorkDay.builder()
                        .start(timeBaseline)
                        .end(timeBaseline.plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(1))
                        .end(timeBaseline.plusDays(1).plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(2))
                        .end(timeBaseline.plusDays(2).plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(3))
                        .end(timeBaseline.plusDays(3).plusHours(7))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(4))
                        .end(timeBaseline.plusDays(4).plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(7))
                        .end(timeBaseline.plusDays(7).plusHours(9))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(8))
                        .end(timeBaseline.plusDays(8).plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(9))
                        .end(timeBaseline.plusDays(9).plusHours(8))
                        .build(),
                WorkDay.builder()
                        .start(timeBaseline.plusDays(10))
                        .end(timeBaseline.plusDays(10).plusHours(8))
                        .build()
        );
        System.out.print(new CalendarPrinter()
                .printMonth(timeBaseline.getDayOfMonth(), weekDays));
    }

}