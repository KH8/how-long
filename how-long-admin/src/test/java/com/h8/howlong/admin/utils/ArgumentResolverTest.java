package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArgumentResolverTest {

    @Test
    void shouldResolveListCommand()
            throws ArgumentResolutionFailedException {
        //given
        var args = Arrays.array("List");

        //when
        var a = new ArgumentResolver(args);
        var result = a.getCommand();

        //then
        assertThat(result).isEqualTo(HowLongAdminCommand.LIST);
    }

    @Test
    void shouldResolveUpdateCommand()
            throws ArgumentResolutionFailedException {
        //given
        var args = Arrays.array("Update");

        //when
        var a = new ArgumentResolver(args);
        var result = a.getCommand();

        //then
        assertThat(result).isEqualTo(HowLongAdminCommand.UPDATE);
    }

    @Test
    void shouldResolveDeleteCommand() throws ArgumentResolutionFailedException {
        //given
        var args = Arrays.array("Delete");

        //when
        var a = new ArgumentResolver(args);
        var result = a.getCommand();

        //then
        assertThat(result).isEqualTo(HowLongAdminCommand.DELETE);
    }

    @Test
    void shouldThrowAnExceptionForUnknownCommand() {
        //given
        var args = Arrays.array("UNKNOWN");

        //when
        var a = new ArgumentResolver(args);
        Throwable thrown = catchThrowable(a::getCommand);

        //then
        assertThat(thrown)
                .isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Command argument 'UNKNOWN' is invalid");
    }

    @Test
    void shouldReturnFullUpdateMode() {
    }

    @Test
    void shouldReturnStartUpdateMode() {
    }

    @Test
    void shouldReturnEndUpdateMode() {
    }


}