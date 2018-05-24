package com.h8.howlong;

import com.h8.howlong.printers.CalendarPrinter;
import com.h8.howlong.printers.ListPrinter;
import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.TimesheetService;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;
import java.time.LocalDateTime;

public class HowLongApplication {

    private static final TimesheetContextService contextService = new TimesheetContextService();

    private static final TimesheetService service = new TimesheetService(contextService);

    public static void main(String[] args) {
        ArgumentResolver arguments = new ArgumentResolver(args);
        if (arguments.calendarMode()) {
            printCalendar(arguments.calendarMonth());
        } else if (arguments.listMode()) {
            printList(arguments.calendarMonth());
        } else {
            printDefault();
        }
    }

    private static void printCalendar(Integer calendarMonth) {
        System.out.println(
                "-----------------------------------------------------------------------------------");
        System.out.print(new CalendarPrinter()
                .printMonth(
                        calendarMonth,
                        contextService.getTimesheets().values()));
        System.out.println(
                "-----------------------------------------------------------------------------------");
        Duration total = contextService.getTotalWorkingTime(calendarMonth);
        System.out.println("Total: " + DurationUtils.format(total));
    }

    private static void printList(Integer calendarMonth) {
        System.out.println(
                "-------------------------------------");
        System.out.print(new ListPrinter()
                .printMonth(
                        calendarMonth,
                        contextService.getTimesheets().values()));
        System.out.println(
                "-------------------------------------");
        Duration total = contextService.getTotalWorkingTime(calendarMonth);
        System.out.println("Total: " + DurationUtils.format(total));
    }

    private static void printDefault() {
        LocalDateTime startTime = service.updateWorkDay().getStart();
        System.out.println("Today is " + startTime.toLocalDate());
        System.out.println("- started at: " + startTime.toLocalTime());
        System.out.println("- elapsed time: " + DurationUtils.format(service.getElapsedTime()));
        System.out.println("- remaining time: " + DurationUtils.format(service.getRemainingTime()));
        System.out.println("Enjoy the day!");
    }

}