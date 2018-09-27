package com.h8.howlong.admin.commands;

import com.h8.howlong.admin.commands.impl.*;
import com.h8.howlong.admin.configuration.*;
import com.h8.howlong.admin.services.*;
import com.h8.howlong.admin.utils.*;
import org.junit.jupiter.api.*;

import java.time.*;

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

//        when
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
    void shouldProduceDeleteCommand() throws ArgumentResolutionFailedException {
//        given
        var command = HowLongAdminCommand.DELETE;

//        when
        when(args.getCommand())
                .thenReturn(command);

        var c = commandFactory.resolveCommand(args);


//        then
        assertThat(c).isInstanceOf(DeleteCommand.class);

    }

    @Test
    void shouldProduceUpdateStartCommand() throws ArgumentResolutionFailedException {

//        given
        var command = HowLongAdminCommand.UPDATE;
        var updateMode = HowLongAdminUpdateMode.START;
        var month = 5;
        var day = 10;
        LocalTime startTime = LocalTime.of(16, 15, 15);

//        when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getUpdateMode())
                .thenReturn(updateMode);


        var c = commandFactory.resolveCommand(args);
        var updateStartCommand = (UpdateStartCommand) c;


//        than
        assertThat(c).isInstanceOf(UpdateStartCommand.class);

        assertThat(updateStartCommand.getMonth()).isEqualTo(month);
        assertThat(updateStartCommand.getDay()).isEqualTo(day);
        assertThat(updateStartCommand.getStart()).isEqualTo(startTime);

    }

    @Test
    void shouldProduceUpdateEndCommand() throws ArgumentResolutionFailedException {

//        given
        var command = HowLongAdminCommand.UPDATE;
        var updateMode = HowLongAdminUpdateMode.END;
        var month = 5;
        var day = 10;
        LocalTime endTime = LocalTime.of(16, 15, 15);

//        when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getUpdateMode())
                .thenReturn(updateMode);


        var c = commandFactory.resolveCommand(args);
        var updateEndCommand = (UpdateEndCommand) c;


//        than
        assertThat(c).isInstanceOf(UpdateEndCommand.class);

        assertThat(updateEndCommand.getMonth()).isEqualTo(month);
        assertThat(updateEndCommand.getDay()).isEqualTo(day);
        assertThat(updateEndCommand.getEnd()).isEqualTo(endTime);

    }

    @Test
    void shouldProduceUpdateFullCommand() throws ArgumentResolutionFailedException {
        //        given
        var command = HowLongAdminCommand.UPDATE;
        var updateMode = HowLongAdminUpdateMode.FULL;
        var month = 5;
        var day = 10;
        LocalTime startTime = LocalTime.of(16, 10, 15);
        LocalTime endTime = LocalTime.of(16, 15, 15);

//        when
        when(args.getCommand())
                .thenReturn(command);
        when(args.getUpdateMode())
                .thenReturn(updateMode);


        var c = commandFactory.resolveCommand(args);
        var updateFullCommand = (UpdateFullCommand) c;


//        than
        assertThat(c).isInstanceOf(UpdateFullCommand.class);

        assertThat(updateFullCommand.getMonth()).isEqualTo(month);
        assertThat(updateFullCommand.getDay()).isEqualTo(day);
        assertThat(updateFullCommand.getStart()).isEqualTo(startTime);
        assertThat(updateFullCommand.getEnd()).isEqualTo(endTime);

    }

}