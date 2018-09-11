package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.HowLongAdminCommands;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

@Getter
public class ArgumentResolver {

    private final String[] args;

    private int day;
    private int month;
    private String updateMode;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public Boolean listMode() {
        if (args.length > 0 && HowLongAdminCommands.LIST.equals(args[0].toUpperCase())) {
            if (args.length > 1) {
                if (0 < Integer.parseInt(args[1]) && 12 >= Integer.parseInt(args[1])) {
                    this.month = Integer.parseInt(args[1]);
                } else
                    throw new IllegalArgumentException("Second argument of list mode has to be an integer from 1 to 12 as it stands for the month of the year");
            } else {
                var cal = Calendar.getInstance();
                this.month = cal.get(Calendar.MONTH);
            }
            return true;
        } else return false;
    }

    public Boolean updateMode() {
        if (args.length > 0 && HowLongAdminCommands.UPDATE.equals(args[0].toUpperCase())) {
            this.month = Integer.parseInt(args[1]);
            this.day = Integer.parseInt(args[2]);
            this.date = LocalDate.of(LocalDate.now().getYear(), month, day);
            this.updateMode = args[3];
            switch (updateMode) {
                case "FULL":
                    this.startTime = LocalDateTime.of(date, LocalTime.parse(args[4]));
                    this.endTime = LocalDateTime.of(date, LocalTime.parse(args[5]));
                    break;
                case "START":
                    this.startTime = LocalDateTime.of(date, LocalTime.parse(args[4]));
                    break;
                case "END":
                    this.endTime = LocalDateTime.of(date, LocalTime.parse(args[5]));
                    break;
                default:
                    throw new IllegalArgumentException("The third argument of an update command has to be FULL, START or END");
            }
            return true;
        } else return false;
    }

    public Boolean deleteMode() {
        if (args.length == 3 && HowLongAdminCommands.DELETE.equals(args[0].toUpperCase())) {
            this.month = Integer.parseInt(args[1]);
            this.day = Integer.parseInt(args[2]);
            return true;
        } else return false;

    }

//    private void printUsage() {
//
//    }

}