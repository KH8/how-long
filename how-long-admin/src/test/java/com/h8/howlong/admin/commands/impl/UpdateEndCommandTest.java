package com.h8.howlong.admin.commands.impl;

import com.h8.howlong.admin.commands.CommandResultStatus;
import com.h8.howlong.admin.services.TimesheetManagementFailedException;
import com.h8.howlong.admin.services.TimesheetManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalTime;
import java.util.Calendar;

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
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        end = LocalTime.now();
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
        verify(timesheetManagementService, times(1))
                .updateEndTime(monthCaptor.capture(), dayCaptor.capture(), endCaptor.capture());

        assertThat(monthCaptor.getValue()).isEqualTo(month);
        assertThat(dayCaptor.getValue()).isEqualTo(day);
        assertThat(endCaptor.getValue()).isEqualTo(end);
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d'.'%d' has been updated", day, month))
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
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d'.'%d' could not be updated because of an exception: test", day, month))
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.ERROR);
    }
}

