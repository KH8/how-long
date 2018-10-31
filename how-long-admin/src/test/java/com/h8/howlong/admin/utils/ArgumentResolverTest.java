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
import java.time.LocalTime;
import java.util.Calendar;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArgumentResolverTest {

    private int currentMonth;
    private int currentDay;

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

    @BeforeEach
    void setUp() {
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
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
    void shouldReturnProperDayArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array(String.format("--day=%d", currentDay), String.format("--month=%d", currentMonth));

        //when
        var ar = new ArgumentResolver(arguments);
        var dayReturned = ar.getDay();

        //then
        assertThat(dayReturned).isEqualTo(currentDay);
    }

    @Test
    void shouldThrowAnExceptionForMissingDayArgument() {
        //given
        var args = Arrays.array("DELETE", String.format("--month=%d", currentMonth));

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
        var arguments = Arrays.array(String.format("--day=%d", currentDay), String.format("--month=%d", currentMonth));

        //when
        var ar = new ArgumentResolver(arguments);
        var monthReturned = ar.getMonth();

        //then
        assertThat(monthReturned).isEqualTo(currentMonth);
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
    void shouldReturnProperStartTimeArgument()
            throws ArgumentResolutionFailedException {
        //given
        var arguments = Arrays.array("UPDATE", String.format("--day=%d", currentDay),
                String.format("--month=%d", currentMonth), "--start-time=11:11:11");

        //when
        var ar = new ArgumentResolver(arguments);
        var startTime = ar.getStartTime();

        //then
        assertThat(startTime).hasValue(LocalTime.of(11, 11, 11));
    }

    @Test
    void shouldThrowAnExceptionForIllegalTimeFormat() {
        //given
        var args = Arrays.array("UPDATE", String.format("--day=%d", currentDay),
                String.format("--month=%d", currentMonth), "--start-time=11.11.11");

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
        var arguments = Arrays.array("UPDATE", String.format("--day=%d", currentDay),
                String.format("--month=%d", currentMonth), "--end-time=12:12:12");

        //when
        var ar = new ArgumentResolver(arguments);
        var endTime = ar.getEndTime();

        //then
        assertThat(endTime).hasValue(LocalTime.of(12, 12, 12));
    }
}