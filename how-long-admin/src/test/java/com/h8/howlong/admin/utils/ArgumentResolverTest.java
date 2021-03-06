package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArgumentResolverTest {

    private LocalTime currentTime;
    private Integer currentMonth;
    private Integer currentDay;

    @BeforeEach
    void setUp() {
        var current = LocalDateTime.now();
        currentMonth = current.getMonthValue();
        currentDay = current.getDayOfMonth();
        currentTime = current.toLocalTime().truncatedTo(ChronoUnit.SECONDS);
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

    static private Stream<Arguments> resolveCommandArgumentsProvider() {
        return Stream.of(
                Arguments.of("List", HowLongAdminCommand.LIST),
                Arguments.of("Update", HowLongAdminCommand.UPDATE),
                Arguments.of("Delete", HowLongAdminCommand.DELETE)
        );
    }

    @Test
    void shouldReturnProperDayArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array(
                param("day", currentDay.toString()),
                param("month", currentMonth.toString()));

        //when
        var ar = new ArgumentResolver(arguments);
        var dayReturned = ar.getDay();

        //then
        assertThat(dayReturned).isEqualTo(currentDay);
    }

    @Test
    void shouldThrowAnExceptionForMissingDayArgument() {
        //given
        var args = Arrays.array("DELETE",
                param("month", currentMonth.toString()));

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
        var arguments = Arrays.array(
                param("day", currentDay.toString()),
                param("month", currentMonth.toString()));

        //when
        var ar = new ArgumentResolver(arguments);
        var monthReturned = ar.getMonth();

        //then
        assertThat(monthReturned).isEqualTo(currentMonth);
    }

    @Test
    void shouldReturnProperCurrentMonthIfMonthArgumentIsMissing()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("LIST");

        //when
        var ar = new ArgumentResolver(arguments);
        var monthReturned = ar.getMonth();

        //then
        assertThat(monthReturned).isEqualTo(LocalDate.now().getMonthValue());
    }

    @ParameterizedTest(name = "day = {arguments}, month = 9")
    @ValueSource(ints = {0, 31})
    void shouldThrowAnExceptionForAGivenImproperDayOfAMonth(Integer day) {
        //given
        var arguments = Arrays.array("List",
                param("day", day.toString()),
                param("month", "9"));

        //when
        var a = new ArgumentResolver(arguments);
        var thrown = catchThrowable(a::getDay);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage(String.format("Day value '%s' is invalid for month '9'", day));
    }

    @ParameterizedTest(name = "month = {arguments}")
    @ValueSource(ints = {0, 13})
    void shouldThrowAnExceptionForAGivenImproperMonthArgument(Integer month) {
        //given
        var arguments = Arrays.array("List",
                param("month", month.toString()));

        //when
        var a = new ArgumentResolver(arguments);
        var thrown = catchThrowable(a::getMonth);

        //then
        assertThat(thrown).isInstanceOf(ArgumentResolutionFailedException.class)
                .hasMessage(String.format("Month value '%d' is invalid", month));
    }

    @Test
    void shouldReturnProperStartTimeArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("UPDATE",
                param("day", currentDay.toString()),
                param("month", currentMonth.toString()),
                param("start-time", currentTime.toString()));

        //when
        var ar = new ArgumentResolver(arguments);
        var startTime = ar.getStartTime();

        //then
        assertThat(startTime).hasValue(currentTime);
    }

    @Test
    void shouldThrowAnExceptionForIllegalTimeFormat() {
        //given
        var args = Arrays.array("UPDATE",
                param("day", currentDay.toString()),
                param("month", currentMonth.toString()),
                param("start-time", "11.11.11"));

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
        var arguments = Arrays.array("UPDATE",
                param("day", currentDay.toString()),
                param("month", currentMonth.toString()),
                param("end-time", currentTime.toString()));

        //when
        var ar = new ArgumentResolver(arguments);
        var endTime = ar.getEndTime();

        //then
        assertThat(endTime).hasValue(currentTime);
    }

    private String param(String paramName, String paramValue) {
        return String.format("--%s=%s", paramName, paramValue);
    }
}