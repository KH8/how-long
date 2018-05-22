package com.h8.howlong;

import com.h8.howlong.services.TimesheetContextService;
import com.h8.howlong.services.TimesheetService;
import com.h8.howlong.utils.CalendarPrinter;
import com.h8.howlong.utils.DurationUtils;

import java.time.Duration;
import java.time.LocalDateTime;

public class HowLongApplication {

    private static final TimesheetContextService contextService = new TimesheetContextService();

    private static final TimesheetService service = new TimesheetService(contextService);

    public static void main(String[] args) {
        ArgumentResolver arguments = new ArgumentResolver(args);
        if (arguments.calendarMode()) {
            System.out.println(
                    "-----------------------------------------------------------------------------------");
            System.out.print(new CalendarPrinter()
                    .printMonth(
                            arguments.calendarMonth(),
                            contextService.getTimesheets().values()));
            System.out.println(
                    "-----------------------------------------------------------------------------------");
            Duration total = contextService.getTotalWorkingTime(arguments.calendarMonth());
            System.out.println("Total: " + DurationUtils.format(total));
        } else {
            LocalDateTime startTime = service.updateWorkDay().getStart();
            System.out.println("Today is " + startTime.toLocalDate());
            System.out.println("- started at: " + startTime.toLocalTime());
            System.out.println("- elapsed time: " + DurationUtils.format(service.getElapsedTime()));
            System.out.println("- remaining time: " + DurationUtils.format(service.getRemainingTime()));
            System.out.println("Enjoy the day!");
        }
    }

}