package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommand;
import com.h8.howlong.admin.configuration.HowLongAdminUpdateMode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

@Getter
public class ArgumentResolver {

    private final String[] args;
    private final String MONTH_ARG_PREFIX = "--month=";
    private final String DAY_ARG_PREFIX = "--day=";
    private final String START_TIME_ARG_PREFIX = "--start-time=";
    private final String END_TIME_ARG_PREFIX = "--end-time=";

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public HowLongAdminCommand getCommand()
            throws ArgumentResolutionFailedException {
        if (args.length < 1) {
            throw new ArgumentResolutionFailedException("Please provide any command");
        }
        var command = args[0].toUpperCase();
        switch (command) {
            case "UPDATE":
                return HowLongAdminCommand.UPDATE;
            case "LIST":
                return HowLongAdminCommand.LIST;
            case "DELETE":
                return HowLongAdminCommand.DELETE;
            default:
                throw new ArgumentResolutionFailedException("Improper command argument");
        }
    }

    public HowLongAdminUpdateMode getUpdateMode() throws ArgumentResolutionFailedException {
        if (getTimeArgument(getSTART_TIME_ARG_PREFIX()).isPresent() && getTimeArgument(getEND_TIME_ARG_PREFIX()).isPresent())
            return HowLongAdminUpdateMode.FULL;
        else if (getTimeArgument(getSTART_TIME_ARG_PREFIX()).isPresent())
            return HowLongAdminUpdateMode.START;
        else if (getTimeArgument(getEND_TIME_ARG_PREFIX()).isPresent())
            return HowLongAdminUpdateMode.END;
        else
            throw new ArgumentResolutionFailedException("Could not find neither start time nor end time of update");
    }

    public int getMonth()
            throws ArgumentResolutionFailedException {
        var month = getIntegerArgument(getMONTH_ARG_PREFIX())
                .orElse(getCurrentMonth());
        return validateMonth(month);
    }

    public int getDay() throws ArgumentResolutionFailedException {
        var day = getMandatoryIntegerArgument(getDAY_ARG_PREFIX());
        return validateIfNumberOfDaysIsValidForAGivenMonth(getMonth(), day);
    }

    public LocalDateTime getStartTime() throws ArgumentResolutionFailedException {
        return getDateTimeArgument(getSTART_TIME_ARG_PREFIX());
    }

    public LocalDateTime getEndTime() throws ArgumentResolutionFailedException {
        return getDateTimeArgument(getEND_TIME_ARG_PREFIX());
    }

    private LocalDateTime getDateTimeArgument(String prefix) throws ArgumentResolutionFailedException {
        return LocalDateTime.of(getLocalDate(), getTimeArgument(prefix).get());
    }

    private int getMandatoryIntegerArgument(String prefix) throws ArgumentResolutionFailedException {
        return getIntegerArgument(prefix)
                .orElseThrow(() -> {
                    var message = String.format("Could not find '%s' argument", prefix.replace("=", ""));
                    return new ArgumentResolutionFailedException(message);
                });
    }

    private Optional<Integer> getIntegerArgument(String prefix) throws ArgumentResolutionFailedException {
        try {
            return getArgument(prefix)
                    .map(Integer::parseInt);
        } catch (NumberFormatException e) {
            var message = String.format("Could not parse '%s' argument", prefix.replace("=", ""));
            throw new ArgumentResolutionFailedException(message);
        }
    }

    private Optional<LocalTime> getTimeArgument(String prefix) throws ArgumentResolutionFailedException {
        try {
            return getArgument(prefix)
                    .map(LocalTime::parse);
        } catch (Exception e) {
            var message = String.format("Could not parse '%s' argument", prefix.replace("=", ""));
            throw new ArgumentResolutionFailedException(message);
        }
    }

    private LocalDate getLocalDate() throws ArgumentResolutionFailedException {
        return LocalDate.of(LocalDate.now().getYear(), getMonth(), getDay());
    }

    private Optional<String> getArgument(String prefix) {
        return Arrays.stream(args)
                .filter(a -> a.startsWith(prefix))
                .map(a -> a.replaceFirst(prefix, ""))
                .findAny();
    }

    private int getNumberOfDaysOfAGivenMonth(int month) {
        return YearMonth
                .of(Calendar
                        .getInstance()
                        .get(Calendar
                                .YEAR), month)
                .lengthOfMonth();
    }

    private int getCurrentMonth() {
        var cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    private int validateIfNumberOfDaysIsValidForAGivenMonth(int month, int day) throws ArgumentResolutionFailedException {
        if (0 < day && day <= getNumberOfDaysOfAGivenMonth(month))
            return day;
        else throw new ArgumentResolutionFailedException("Number of days cannot be applied to a given month");
    }

    private int validateMonth(int month) throws ArgumentResolutionFailedException {
        if (0 < month && month < 13) {
            return month;
        } else throw new ArgumentResolutionFailedException("Month value is out of range");
    }
}