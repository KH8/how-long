package com.h8.howlong;

import java.time.LocalDate;

class ArgumentResolver {

    private String[] args;

    ArgumentResolver(String[] args) {
        this.args = args;
    }

    Boolean calendarMode() {
        return args.length > 0 && "calendar".equals(args[0]);
    }

    Boolean listMode() {
        return args.length > 0 && "list".equals(args[0]);
    }

    Integer calendarMonth() {
        return args.length > 1 ?
                Integer.parseInt(args[1]) : LocalDate.now().getMonthValue();
    }

}