package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.*;
import org.assertj.core.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.time.*;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.*;

class ArgumentResolverTest {

    static private Stream<Arguments> resolveCommandArgumentsProvider() {
        return Stream.of(
                Arguments.of("List", HowLongAdminCommand.LIST),
                Arguments.of("Update", HowLongAdminCommand.UPDATE),
                Arguments.of("Delete", HowLongAdminCommand.DELETE)
        );
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

    @ParameterizedTest(name = "command = {0}")
    @MethodSource("resolveCommandArgumentsProvider")
    void shouldResolveCommand(String commandName, Enum command)
            throws ArgumentResolutionFailedException {
        //given
        var args = Arrays.array(commandName);

        //when
        var a = new ArgumentResolver(args);
        var result = a.getCommand();

        //then
        assertThat(result).isEqualTo(command);
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

    @ParameterizedTest(name = "day = {arguments}, month = 9")
    @ValueSource(ints = {0, 31})
    void shouldThrowAnExceptionForAGivenImproperDayOfAMonth(int day) {
        //given
        var arguments = Arrays.array("List", String.format("--day=%s", day), "--month=9");

        //when
        var a = new ArgumentResolver(arguments);
        var thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage(String.format("Day value '%s' is invalid for month '9'", day));
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

    @ParameterizedTest(name = "month = {arguments}")
    @ValueSource(ints = {0, 13})
    void shouldThrowAnExceptionForAGivenImproperMonthArgument(int month) {
        //given
        var arguments = Arrays.array("List", String.format("--month=%s", month));

        //when
        var a = new ArgumentResolver(arguments);
        var thrown = catchThrowable(a::getMonth);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage(String.format("Month value '%d' is invalid", month));
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

    @Test
    void shouldReturnProperEndTimeArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("UPDATE", "--month=2", "--day=2", "--end-time=12:12:12");

        //when
        var ar = new ArgumentResolver(arguments);
        var endTime = ar.getEndTime();

        //then
        assertThat(endTime).hasValue(LocalTime.of(12, 12, 12));
    }
}