package com.h8.howlong.admin.commands;

import com.h8.howlong.admin.commands.impl.*;
import com.h8.howlong.admin.configuration.*;
import com.h8.howlong.admin.services.*;
import com.h8.howlong.admin.utils.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandFactoryTest {

    private ArgumentResolver args;

    private TimesheetManagementService managementService;

    private CommandFactory commandFactory;

    @BeforeEach
    void setUp() {
        args = mock(ArgumentResolver.class);
        managementService = mock(TimesheetManagementService.class);
        commandFactory = new CommandFactory(managementService);
    }

    @Test
    void shouldProduceListCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.LIST;
        var month = 10;

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);

        var c = commandFactory.resolveCommand(args);

        //then
        assertThat(c).isInstanceOf(ListCommand.class);

        var listCommand = (ListCommand) c;
        assertThat(listCommand.getTimesheetManagementService()).isEqualTo(managementService);
        assertThat(listCommand.getMonth()).isEqualTo(month);
    }

    @Test
    void shouldProduceDeleteCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.DELETE;
        var month = 10;
        var day = 22;

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);
        when(args.getDay())
                .thenReturn(day);


        var c = commandFactory.resolveCommand(args);


        //then
        assertThat(c).isInstanceOf(DeleteCommand.class);

        var deleteCommand = (DeleteCommand) c;
        assertThat(deleteCommand.getTimesheetManagementService())
                .isEqualTo(managementService);
        assertThat(deleteCommand.getMonth())
                .isEqualTo(month);
        assertThat(deleteCommand.getDay())
                .isEqualTo(day);
    }

    @Test
    void shouldProduceUpdateStartCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.UPDATE;
        var month = 5;
        var day = 10;
        var startTime = LocalTime.now();

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);
        when(args.getDay())
                .thenReturn(day);
        when(args.getStartTime())
                .thenReturn(Optional.of(startTime));

        var c = commandFactory.resolveCommand(args);

        //than
        assertThat(c).isInstanceOf(UpdateStartCommand.class);

        var updateStartCommand = (UpdateStartCommand) c;
        assertThat(updateStartCommand.getMonth()).isEqualTo(month);
        assertThat(updateStartCommand.getDay()).isEqualTo(day);
        assertThat(updateStartCommand.getStart()).isEqualTo(startTime);

    }

    @Test
    void shouldProduceUpdateEndCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.UPDATE;
        var month = 5;
        var day = 10;
        var endTime = LocalTime.now();

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);
        when(args.getDay())
                .thenReturn(day);
        when(args.getEndTime())
                .thenReturn(Optional.of(endTime));

        var c = commandFactory.resolveCommand(args);

        //than
        assertThat(c).isInstanceOf(UpdateEndCommand.class);

        var updateEndCommand = (UpdateEndCommand) c;
        assertThat(updateEndCommand.getMonth()).isEqualTo(month);
        assertThat(updateEndCommand.getDay()).isEqualTo(day);
        assertThat(updateEndCommand.getEnd()).isEqualTo(endTime);

    }

    @Test
    void shouldProduceUpdateFullCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.UPDATE;
        var month = 5;
        var day = 10;
        var startTime = LocalTime.now().minusHours(1);
        var endTime = LocalTime.now();

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);
        when(args.getDay())
                .thenReturn(day);
        when(args.getStartTime())
                .thenReturn(Optional.of(startTime));
        when(args.getEndTime())
                .thenReturn(Optional.of(endTime));

        var c = commandFactory.resolveCommand(args);

        //than
        assertThat(c).isInstanceOf(UpdateFullCommand.class);

        var updateFullCommand = (UpdateFullCommand) c;
        assertThat(updateFullCommand.getMonth()).isEqualTo(month);
        assertThat(updateFullCommand.getDay()).isEqualTo(day);
        assertThat(updateFullCommand.getStart()).isEqualTo(startTime);
        assertThat(updateFullCommand.getEnd()).isEqualTo(endTime);
    }


    @Test
    void shouldThrowAnExceptionWhenCommandArgumentCannotBeResolved() {
        fail("test not implemented");
    }


    @Test
    void shouldThrowAnExceptionForMissingUpdateTimes() {
        fail("test not implemented");
    }

}