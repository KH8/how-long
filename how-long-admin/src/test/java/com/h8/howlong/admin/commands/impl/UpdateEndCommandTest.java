package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UpdateEndCommandTest {
    private TimesheetManagementService timesheetManagementService;

    private UpdateEndCommand updateEndCommand;

    private int month;
    private int day;
    private LocalTime end;

    @BeforeEach
    void setUp() {
        timesheetManagementService = mock(TimesheetManagementService.class);
        month = 9;
        day = 30;
        end = LocalTime.of(1, 1);
        updateEndCommand = new UpdateEndCommand(timesheetManagementService, month, day, end);
    }

    @Test
    void shouldCallUpdateEndOnTimesheetManagementServiceAndReturnCommandResultOk()
            throws TimesheetManagementFailedException {
        //when
        var commandResult = updateEndCommand.execute();

        //then
        var monthCaptor = ArgumentCaptor.forClass(Integer.class);
        var dayCaptor = ArgumentCaptor.forClass(Integer.class);
        var endCaptor = ArgumentCaptor.forClass(LocalTime.class);
        verify(timesheetManagementService, times(1)).updateEndTime(monthCaptor.capture(), dayCaptor.capture(), endCaptor.capture());

        assertThat(monthCaptor.getValue()).isEqualTo(month);
        assertThat(dayCaptor.getValue()).isEqualTo(day);
        assertThat(endCaptor.getValue()).isEqualTo(end);
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message", "The day '30'.'9' has been updated")
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.SUCCESS);
    }

    @Test
    void shouldReturnCommandResultErrorWhenTimeSheetManagementExceptionIsThrown()
            throws TimesheetManagementFailedException {
        //when
        doThrow(new TimesheetManagementFailedException("test"))
                .when(timesheetManagementService).updateEndTime(month, day, end);

        var commandResult = updateEndCommand.execute();

        //then
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message", "The day '30'.'9' could not be updated because of an exception: test")
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.ERROR);
    }
}

