package com.h8.howlong;

import java.time.LocalDate;

public class ArgumentResolver {

    private String[] args;

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public Boolean calendarMode() {
        return args.length > 0 && "calendar".equals(args[0]);
    }

    public Integer calendarMonth() {
        return calendarMode() && args.length > 1 ?
                Integer.parseInt(args[1]) : LocalDate.now().getMonthValue();
    }

}