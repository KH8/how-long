package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UpdateStartCommandTest {

    private TimesheetManagementService timesheetManagementService;

    private UpdateStartCommand updateStartCommand;

    private int month;
    private int day;
    private LocalTime start;

    @BeforeEach
    void setUp() {
        timesheetManagementService = mock(TimesheetManagementService.class);
        var current = LocalDateTime.now();
        start = current.toLocalTime();
        month = current.getMonthValue();
        day = current.getDayOfMonth();
        updateStartCommand = new UpdateStartCommand(timesheetManagementService, month, day, start);
    }

    @Test
    void shouldCallUpdateStartOnTimesheetManagementServiceAndReturnCommandResultOk()
            throws TimesheetManagementFailedException {
        //when
        var commandResult = updateStartCommand.execute();

        //then
        var monthCaptor = ArgumentCaptor.forClass(Integer.class);
        var dayCaptor = ArgumentCaptor.forClass(Integer.class);
        var startCaptor = ArgumentCaptor.forClass(LocalTime.class);
        verify(timesheetManagementService, times(1)).
                updateStartTime(monthCaptor.capture(), dayCaptor.capture(), startCaptor.capture());

        assertThat(monthCaptor.getValue()).isEqualTo(month);
        assertThat(dayCaptor.getValue()).isEqualTo(day);
        assertThat(startCaptor.getValue()).isEqualTo(start);
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d.%d' has been updated", day, month))
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.SUCCESS);
    }

    @Test
    void shouldReturnCommandResultErrorWhenTimeSheetManagementExceptionIsThrown()
            throws TimesheetManagementFailedException {
        //when
        doThrow(new TimesheetManagementFailedException("test"))
                .when(timesheetManagementService).updateStartTime(month, day, start);

        var commandResult = updateStartCommand.execute();

        //then
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d.%d' could not be updated because of an exception: test", day, month))
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.ERROR);
    }
}