package com.h8.howlong.admin.utils;

import com.h8.howlong.admin.configuration.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
public class ArgumentResolver {

    private final String[] args;

//    private int day;
//    private int month;
//    private String command;
//    private String updateMode;
//    private LocalDate date;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;

    public ArgumentResolver(String[] args) {
        this.args = args;
    }

    public HowLongAdminCommand getCommand()
            throws ArgumentResolutionFailedException {
        var command = args[0].toUpperCase();
        switch (command) {
            case "UPDATE":
                return HowLongAdminCommand.UPDATE;
            case "LIST":
                return HowLongAdminCommand.UPDATE;
            case "DELETE":
                return HowLongAdminCommand.DELETE;
            default:
                throw new ArgumentResolutionFailedException("Improper command argument");
        }
    }

    public HowLongAdminUpdateMode getUpdateMode()
            throws ArgumentResolutionFailedException {
        var updateMode = args[3].toUpperCase();
        switch (updateMode) {
            case "FULL":
                return HowLongAdminUpdateMode.FULL;
            case "START":
                return HowLongAdminUpdateMode.START;
            case "END":
                return HowLongAdminUpdateMode.END;
            default:
                throw new ArgumentResolutionFailedException("Improper update mode argument");
        }
    }

    public Integer getMonth()
            throws ArgumentResolutionFailedException {
        Integer month = null;
        if (args.length > 1) {
            try {
                if (0 < Integer.parseInt(args[1]) && 12 >= Integer.parseInt(args[1])) {
                    month = Integer.parseInt(args[1]);
                }
            } catch (Exception e) {
                throw new ArgumentResolutionFailedException("Improper month value ");
            }
        } else {
            var cal = Calendar.getInstance();
            month = cal.get(Calendar.MONTH) + 1;
        }
        return month;
    }

    public Integer getDay()
            throws ArgumentResolutionFailedException {
        try {
            if (numberOfDaysIsValidForAGivenMonth(Integer.parseInt(args[1]), Integer.parseInt(args[2]))) {
                return Integer.parseInt(args[2]);
            } else {
                throw new ArgumentResolutionFailedException("Improper day value ");
            }
        } catch (Exception e) {
            throw new ArgumentResolutionFailedException("Improper day value ");
        }
    }

    public LocalDateTime getStartTime()
            throws ArgumentResolutionFailedException {
        try {
            return LocalDateTime.of(getDate(), LocalTime.parse(args[4]));
        } catch (Exception e) {
            throw new ArgumentResolutionFailedException("Improper start time argument");
        }
    }

    //Problem opisany w pytaniach na google drive
    public LocalDateTime getEndTime()
            throws ArgumentResolutionFailedException {
        var updateMode = getUpdateMode();
        try {
            if (updateMode.equals(HowLongAdminUpdateMode.FULL)) {
                return LocalDateTime.of(getDate(), LocalTime.parse(args[5]));
            } else return LocalDateTime.of(getDate(), LocalTime.parse(args[4]));
        } catch (Exception e) {
            throw new ArgumentResolutionFailedException("Improper end time argument");
        }
    }

    private int getNumberOfDaysOfAGivenMonth(int month) {
        return YearMonth
                .of(Calendar
                        .getInstance()
                        .get(Calendar
                                .YEAR), month)
                .lengthOfMonth();
    }

    private boolean numberOfDaysIsValidForAGivenMonth(int month, int day) {
        return 0 < day && day <= getNumberOfDaysOfAGivenMonth(month);
    }

    private LocalDate getDate() throws ArgumentResolutionFailedException {
        return LocalDate.of(LocalDate.now().getYear(), getMonth(), getDay());
    }



//    public Boolean updateMode() {
//        if (args.length > 0 && HowLongAdminCommand.UPDATE.equals(args[0].toUpperCase())) {
//            try {
//                this.month = Integer.parseInt(args[1]);
//                this.day = Integer.parseInt(args[2]);
//                this.date = LocalDate.of(LocalDate.now().getYear(), month, day);
//                this.updateMode = args[3].toUpperCase();
//                switch (updateMode) {
//                    case "FULL":
//                        this.startTime = LocalDateTime.of(date, LocalTime.parse(args[4]));
//                        this.endTime = LocalDateTime.of(date, LocalTime.parse(args[5]));
//                        break;
//                    case "START":
//                        this.startTime = LocalDateTime.of(date, LocalTime.parse(args[4]));
//                        break;
//                    case "END":
//                        this.endTime = LocalDateTime.of(date, LocalTime.parse(args[4]));
//                        break;
//                    default:
//                        throw new IllegalArgumentException("The third argument of an update command has to be FULL, START or END");
//                }
//            } catch (Exception e) {
//                invalidArgumentsExceptionHandling(e);
//            }
//            return true;
//        } else return false;
//    }
//
//    public Boolean deleteMode() {
//        if (args.length > 0 && HowLongAdminCommand.DELETE.equals(args[0].toUpperCase())) {
//            try {
//                this.month = Integer.parseInt(args[1]);
//                this.day = Integer.parseInt(args[2]);
//            } catch (Exception e) {
//                invalidArgumentsExceptionHandling(e);
//            }
//            return true;
//        } else return false;
//
//    }
//
//    private void invalidArgumentsExceptionHandling(Exception e) {
//        Logger.log("Command recognized, although exception appeared:" + e.toString());
//        Logger.log("Invalid argument type. Nothing has been changed. Please refer to README file for more details.");
//        System.exit(0);
//    }
//
}