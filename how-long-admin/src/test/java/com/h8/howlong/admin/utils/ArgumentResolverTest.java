package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.*;
import org.assertj.core.util.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

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
    void shouldResolveDeleteCommand()
            throws ArgumentResolutionFailedException {
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
                .hasMessage("Unknown command 'UNKNOWN'");
    }

    @Test
    void shouldReturnProperDayArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("--day=30","--month=9");

        //when
        var ar = new ArgumentResolver(arguments);
        var day =  ar.getDay();

        //then
        assertThat(day).isEqualTo(30);
    }

    @Test
    void shouldThrowAnExceptionForDayArgumentAboveUpperBound() {
        //given
        var args = Arrays.array("LIST", "--day=31","--month=9");

        //when
        var a = new ArgumentResolver(args);
        Throwable thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Day value '31' is invalid for month '9'");
    }


    @Test
    void shouldThrowAnExceptionForDayArgumentBelowLowerBound() {
        fail("test not implemented");
    }

    @Test
    void shouldThrowAnExceptionForMissingDayArgument() {
        fail("test not implemented");
    }

    @Test
    void shouldReturnProperMonthArgument() {
        fail("test not implemented");
    }

    @Test
    void shouldReturnProperCurrentMonthIfMonthArgumentIsMissing() {
        fail("test not implemented");
    }

    @Test
    void shouldThrowAnExceptionForMonthArgumentAboveUpperBound() {
        //given
        var args = Arrays.array("LIST", "--month=13");

        //when
        var a = new ArgumentResolver(args);
        Throwable thrown = catchThrowable(a::getMonth);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Month value '13' is invalid");
    }

    @Test
    void shouldThrowAnExceptionForMonthArgumentBelowLowerBound() {
        fail("test not implemented");
    }

}