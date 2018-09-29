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
                .hasMessage("Improper command argument");
    }

    @Test
    void shouldReturnFullUpdateMode() throws ArgumentResolutionFailedException {

//        given
        var args = Arrays.array("delete", "--start-time=16:10:12", "--end-time=17:15:18");

//        when
        var a = new ArgumentResolver(args);
        var result = a.getUpdateMode();

//        then
        assertThat(result)
                .isEqualTo(HowLongAdminUpdateMode.FULL);
    }

    @Test
    void shouldReturnStartUpdateMode() throws ArgumentResolutionFailedException {

//        given
        var args = Arrays.array("delete", "--start-time=16:10:12");

//        when
        var a = new ArgumentResolver(args);
        var result = a.getUpdateMode();

//        then
        assertThat(result)
                .isEqualTo(HowLongAdminUpdateMode.START);
    }

    @Test
    void shouldReturnEndUpdateMode() throws ArgumentResolutionFailedException {

//        given
        var args = Arrays.array("delete", "--end-time=16:10:12");

//        when
        var a = new ArgumentResolver(args);
        var result = a.getUpdateMode();

//        then
        assertThat(result)
                .isEqualTo(HowLongAdminUpdateMode.END);
    }

    @Test
    void shouldReturnDay() throws ArgumentResolutionFailedException {

//        given
        var arguments = Arrays.array("--day=30","--month=9");

//        when
        var ar = new ArgumentResolver(arguments);
        var day =  ar.getDay();

//        then
        assertThat(day).isEqualTo(30);
    }

    @Test
    void shouldThrowAnExceptionForInvalidDayArgument() {

//        given
        var args = Arrays.array("--day=31","--month=9");

//        when
        var a = new ArgumentResolver(args);
        Throwable thrown = catchThrowable(a::getCommand);

//        then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Improper command argument");
    }

    @Test
    void shouldThrowAnExceptionForInvalidMonthArgument() {

//        given
        var args = Arrays.array("--month=35");

//        when
        var a = new ArgumentResolver(args);
        Throwable thrown = catchThrowable(a::getCommand);

//        then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Improper command argument");
    }

}