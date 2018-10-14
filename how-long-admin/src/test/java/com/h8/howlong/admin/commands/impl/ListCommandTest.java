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
        var month = 9;
        var start = LocalDateTime.of(2018, 9, 23, 1, 1);
        var end = LocalDateTime.of(2018, 9, 23, 1, 2);

        var timesheetManagementService = mock(TimesheetManagementService.class);
        var listCommand = new ListCommand(timesheetManagementService, month);
        var workday = mock(WorkDay.class);
        var list = List.of(workday);
        var pb = PrintBuilder.builder();

        pb.ln(String.format("Timesheet for: <c2018/%02d>", month) + LS + "<c23> | 01:01:00 | 01:02:00 | 00:01");

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