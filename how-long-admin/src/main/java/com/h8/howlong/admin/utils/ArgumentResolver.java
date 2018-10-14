package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Optional;

public class ArgumentResolver {

    private final String[] args;

    private final static String MONTH_ARG = "month";
    private final static String DAY_ARG = "day";
    private final static String START_TIME_ARG = "start-time";
    private final static String END_TIME_ARG = "end-time";

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public HowLongAdminCommand getCommand()
            throws ArgumentResolutionFailedException {
        if (args.length < 1) {
            throw new ArgumentResolutionFailedException("No command provided");
        }
        var command = args[0].toUpperCase();
        switch (command) {
            case "UPDATE":
                return HowLongAdminCommand.UPDATE;
            case "LIST":
                return HowLongAdminCommand.LIST;
            case "DELETE":
                return HowLongAdminCommand.DELETE;
            default: {
                var message = String.format("Unknown command '%s'", args[0]);
                throw new ArgumentResolutionFailedException(message);
            }
        }
    }

    public int getMonth()
            throws ArgumentResolutionFailedException {
        var month = getIntegerArgument(MONTH_ARG)
                .orElse(getCurrentMonth());
        validateMonthArgument(month);
        return month;
    }

    public int getDay()
            throws ArgumentResolutionFailedException {
        var day = getIntegerArgument(DAY_ARG)
                .orElseThrow(() -> {
                    var message = String.format("Could not resolve '%s' argument", DAY_ARG);
                    return new ArgumentResolutionFailedException(message);
                });
        validateDayArgument(getMonth(), day);
        return day;
    }

    public Optional<LocalTime> getStartTime()
            throws ArgumentResolutionFailedException {
        return getLocalTimeArgument(START_TIME_ARG);
    }

    public Optional<LocalTime> getEndTime()
            throws ArgumentResolutionFailedException {
        return getLocalTimeArgument(END_TIME_ARG);
    }

    private Optional<Integer> getIntegerArgument(String prefix)
            throws ArgumentResolutionFailedException {
        try {
            return getArgument(prefix)
                    .map(Integer::parseInt);
        } catch (NumberFormatException e) {
            var message = String.format("Could not parse '%s' argument as Integer", prefix);
            throw new ArgumentResolutionFailedException(message, e);
        }
    }

    private Optional<LocalTime> getLocalTimeArgument(String prefix)
            throws ArgumentResolutionFailedException {
        try {
            return getArgument(prefix)
                    .map(LocalTime::parse);
        } catch (Exception e) {
            var message = String.format("Could not parse '%s' argument as LocalTime", prefix);
            throw new ArgumentResolutionFailedException(message, e);
        }
    }

    private Optional<String> getArgument(String argument) {
        var prefix = String.format("--%s=", argument);
        return Arrays.stream(args)
                .filter(a -> a.startsWith(prefix))
                .map(a -> a.replaceFirst(prefix, ""))
                .findAny();
    }

    private void validateMonthArgument(int month)
            throws ArgumentResolutionFailedException {
        if (month < 1 || month > 12) {
            var message = String.format("Month value '%s' is invalid", month);
            throw new ArgumentResolutionFailedException(message);
        }
    }

    private void validateDayArgument(int month, int day)
            throws ArgumentResolutionFailedException {
        var m = YearMonth.of(getCurrentYear(), month);
        var daysInMonth = m.lengthOfMonth();
        if (day < 1 || day > daysInMonth) {
            var message = String.format("Day value '%s' is invalid for month '%s'", day, month);
            throw new ArgumentResolutionFailedException(message);
        }
    }

    private int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

}