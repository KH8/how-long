package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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
        var thrown = catchThrowable(a::getCommand);

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
        var thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Day value '31' is invalid for month '9'");
    }


    @Test
    void shouldThrowAnExceptionForDayArgumentBelowLowerBound() {
        //given
        var args = Arrays.array("LIST", "--day=0", "--month=9");

        //when
        var a = new ArgumentResolver(args);
        var thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Day value '0' is invalid for month '9'");
    }

    @Test
    void shouldThrowAnExceptionForMissingDayArgument() {
        //given
        var args = Arrays.array("DELETE", "--month=9");

        //when
        var a = new ArgumentResolver(args);
        var thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Could not resolve 'day' argument");
    }

    @Test
    void shouldReturnProperMonthArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("--day=1", "--month=2");

        //when
        var ar = new ArgumentResolver(arguments);
        var month = ar.getMonth();

        //then
        assertThat(month).isEqualTo(2);
    }

    @Test
    void shouldReturnProperCurrentMonthIfMonthArgumentIsMissing()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("LIST");

        //when
        var ar = new ArgumentResolver(arguments);
        var month = ar.getMonth();

        //then
        assertThat(month).isEqualTo(LocalDate.now().getMonthValue());
    }

    @Test
    void shouldThrowAnExceptionForMonthArgumentAboveUpperBound() {
        //given
        var args = Arrays.array("LIST", "--month=13");

        //when
        var a = new ArgumentResolver(args);
        var thrown = catchThrowable(a::getMonth);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Month value '13' is invalid");
    }

    @Test
    void shouldThrowAnExceptionForMonthArgumentBelowLowerBound() {
        //given
        var args = Arrays.array("LIST", "--month=0");

        //when
        var a = new ArgumentResolver(args);
        var thrown = catchThrowable(a::getMonth);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Month value '0' is invalid");
    }

    @Test
    void shouldReturnProperStartTimeArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("UPDATE", "--month=1", "--day=1", "--start-time=11:11:11");

        //when
        var ar = new ArgumentResolver(arguments);
        var startTime = ar.getStartTime();

        //then
        assertThat(startTime).hasValue(LocalTime.of(11, 11, 11));
    }

    @Test
    void shouldReturnProperEndTimeArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("UPDATE", "--month=2", "--day=2", "--end-time=12:12:12");

        //when
        var ar = new ArgumentResolver(arguments);
        var startTime = ar.getEndTime();

        //then
        assertThat(startTime).hasValue(LocalTime.of(12, 12, 12));
    }


    @Test
    void shouldThrowAnExceptionForIllegalTimeFormat() {
        //given
        var args = Arrays.array("UPDATE", "--month=1", "--day=1", "--start-time=11.11.11");

        //when
        var a = new ArgumentResolver(args);
        var thrown = catchThrowable(a::getStartTime);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage("Could not parse 'start-time' argument as LocalTime");
    }


}