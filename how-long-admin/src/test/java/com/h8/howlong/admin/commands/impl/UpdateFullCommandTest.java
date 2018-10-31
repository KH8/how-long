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

class UpdateFullCommandTest {
    private TimesheetManagementService timesheetManagementService;

    private UpdateFullCommand updateFullCommand;

    private int month;
    private int day;
    private LocalTime start;
    private LocalTime end;

    @BeforeEach
    void setUp() {
        timesheetManagementService = mock(TimesheetManagementService.class);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        start = LocalTime.of(1, 0);
        end = LocalTime.of(1, 1);
        updateFullCommand = new UpdateFullCommand(timesheetManagementService, month, day, start, end);
    }

    @Test
    void shouldCallUpdateStartAndUpdateEndOnTimesheetManagementServiceAndReturnCommandResultOk()
            throws TimesheetManagementFailedException {
        //when
        var commandResult = updateFullCommand.execute();

        //then
        var monthStartCaptor = ArgumentCaptor.forClass(Integer.class);
        var dayStartCaptor = ArgumentCaptor.forClass(Integer.class);
        var startCaptor = ArgumentCaptor.forClass(LocalTime.class);
        var monthEndCaptor = ArgumentCaptor.forClass(Integer.class);
        var dayEndCaptor = ArgumentCaptor.forClass(Integer.class);
        var endCaptor = ArgumentCaptor.forClass(LocalTime.class);

        verify(timesheetManagementService, times(1))
                .updateStartTime(monthStartCaptor.capture(), dayStartCaptor.capture(), startCaptor.capture());
        verify(timesheetManagementService, times(1))
                .updateEndTime(monthEndCaptor.capture(), dayEndCaptor.capture(), endCaptor.capture());

        assertThat(monthStartCaptor.getValue()).isEqualTo(month);
        assertThat(dayStartCaptor.getValue()).isEqualTo(day);
        assertThat(startCaptor.getValue()).isEqualTo(start);

        assertThat(monthEndCaptor.getValue()).isEqualTo(month);
        assertThat(dayEndCaptor.getValue()).isEqualTo(day);
        assertThat(endCaptor.getValue()).isEqualTo(end);


        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d'.'%d' has been updated", day, month));
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.SUCCESS);
    }

    @Test
    void shouldReturnCommandResultErrorWhenTimeSheetManagementExceptionIsThrownOnUpdateStartTime()
            throws TimesheetManagementFailedException {
        //when
        doThrow(new TimesheetManagementFailedException("test"))
                .when(timesheetManagementService).updateStartTime(month, day, start);

        var commandResult = updateFullCommand.execute();

        //then
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d'.'%d' could not be updated because of an exception: test", day, month))
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.ERROR);
    }

    @Test
    void shouldReturnCommandResultErrorWhenTimeSheetManagementExceptionIsThrownOnUpdateEndTime()
            throws TimesheetManagementFailedException {
        //when
        doThrow(new TimesheetManagementFailedException("test"))
                .when(timesheetManagementService).updateEndTime(month, day, end);

        var commandResult = updateFullCommand.execute();

        //then
        assertThat(commandResult)
                .hasFieldOrPropertyWithValue("message",
                        String.format("The day '%d'.'%d' could not be updated because of an exception: test", day, month))
                .hasFieldOrPropertyWithValue("status", CommandResultStatus.ERROR);
    }
}