package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.services.TimesheetManagementService;
import com.h8.howlong.domain.WorkDay;
import com.h8.howlong.utils.print.PrintBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {
    private final String LS = System.lineSeparator();

    @Test
    void shouldReturnProperlyFormattedTimesheetForAGivenMonth() {
        //given
        var start = LocalDateTime.now();
        var end = start.plusHours(1);
        var month = start.getMonthValue();

        var timesheetManagementService = mock(TimesheetManagementService.class);
        var listCommand = new ListCommand(timesheetManagementService, month);
        var workday = mock(WorkDay.class);
        var list = List.of(workday);
        var pb = PrintBuilder.builder();

        pb.ln(String.format("Timesheet for: <c%02d/%d>", start.getYear(), month) + LS
                + String.format("<c%02d> | %02d:%02d:%02d | %02d:%02d:%02d | 01:00",
                start.getDayOfMonth(),
                start.getHour(), start.getMinute(), start.getSecond(),
                end.getHour(), end.getMinute(), end.getSecond()));

        //when
        when(timesheetManagementService.getTimesheet(month))
                .thenReturn(list);
        when(workday.getStart())
                .thenReturn(start);
        when(workday.getEnd())
                .thenReturn(end);

        var commandResult = listCommand.execute();

        //then
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message", pb.build())
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.SUCCESS);
    }

}