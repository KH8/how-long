package com.h8.howlong.admin.commands;

import com.h8.howlong.admin.commands.impl.*;
import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import com.h8.howlong.admin.services.TimesheetManagementService;
import com.h8.howlong.admin.utils.ArgumentResolutionFailedException;
import com.h8.howlong.admin.utils.ArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandFactoryTest {

    private ArgumentResolver args;

    private TimesheetManagementService managementService;

    private CommandFactory commandFactory;

    private Integer month;
    private Integer day;

    @BeforeEach
    void setUp() {
        args = mock(ArgumentResolver.class);
        managementService = mock(TimesheetManagementService.class);
        commandFactory = new CommandFactory(managementService);
        var current = LocalDateTime.now();
        month = current.getMonthValue();
        day = current.getDayOfMonth();
    }

    @Test
    void shouldProduceListCommand()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.LIST;

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
        var startTime = LocalTime.now();
        var endTime = LocalTime.now().plusHours(1);

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
    void shouldThrowAnExceptionWhenCommandArgumentCannotBeResolved()
            throws ArgumentResolutionFailedException {
        //when
        when(args.getCommand())
                .thenReturn(null);


        var thrown = catchThrowable(() -> commandFactory.resolveCommand(args));

        //than
        assertThat(thrown)
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowAnExceptionWhenGetCommandMethodThrowsException()
            throws ArgumentResolutionFailedException {
        //given

        //when
        when(args.getCommand())
                .thenThrow(new ArgumentResolutionFailedException("Unknown command 'command'"));

        var thrown = catchThrowable(() -> commandFactory.resolveCommand(args));

        //than
        assertThat(thrown)
                .isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Unknown command 'command'");
    }

    @Test
    void shouldThrowAnExceptionForMissingUpdateTimes()
            throws ArgumentResolutionFailedException {
        //given
        var command = HowLongAdminCommand.UPDATE;
        Optional<LocalTime> startTime = Optional.empty();
        Optional<LocalTime> endTime = Optional.empty();

        //when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getMonth())
                .thenReturn(month);
        when(args.getDay())
                .thenReturn(day);
        when(args.getStartTime())
                .thenReturn(startTime);
        when(args.getEndTime())
                .thenReturn(endTime);

        var thrown = catchThrowable(() -> commandFactory.resolveCommand(args));

        //than
        assertThat(thrown)
                .isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Could not resolve update command for given combination of arguments");
    }
}