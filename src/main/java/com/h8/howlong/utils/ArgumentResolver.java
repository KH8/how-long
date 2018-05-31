package com.h8.howlong.utils;

import com.h8.howlong.configuration.HowLongApplicationCommands;

import java.time.LocalDate;

public class ArgumentResolver {

    private final String[] args;

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public Boolean calendarMode() {
        return args.length > 0 && HowLongApplicationCommands.CALENDAR.equals(args[0]);
    }

    public Boolean listMode() {
        return args.length > 0 && HowLongApplicationCommands.LIST.equals(args[0]);
    }

    public Integer calendarMonth() {
        return args.length > 1 ?
                Integer.parseInt(args[1]) : LocalDate.now().getMonthValue();
    }

}