package com.h8.howlong.admin.commands;

import com.h8.howlong.admin.commands.impl.ListCommand;
import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import com.h8.howlong.admin.services.TimesheetManagementService;
import com.h8.howlong.admin.utils.ArgumentResolutionFailedException;
import com.h8.howlong.admin.utils.ArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandFactoryTest {

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

        var listCommand = (ListCommand)c;
        assertThat(listCommand.getTimesheetManagementService()).isEqualTo(managementService);
        assertThat(listCommand.getMonth()).isEqualTo(month);
    }

    //TODO!!!
    //implement rest of test cases

}