package com.h8.howlong;

import com.h8.howlong.printers.PrintingService;

public class HowLongApplication {

    private static final HowLongApplicationContext applicationContext;

    static {
        applicationContext = new HowLongApplicationContext();
    }

    public static void main(String[] args) {
        applicationContext.getTimesheetService().updateWorkDay();
        ArgumentResolver arguments = new ArgumentResolver(args);
        PrintingService service = resolvePrinter(arguments);
        System.out.print(service.print(arguments.calendarMonth()));
    }

    private static PrintingService resolvePrinter(ArgumentResolver arguments) {
        if (arguments.calendarMode()) {
            return applicationContext.getPrintingServiceFactory()
                    .getCalendarPrinter();
        } else if (arguments.listMode()) {
            return applicationContext.getPrintingServiceFactory()
                    .getListPrinter();
        } else {
            return applicationContext.getPrintingServiceFactory().getDefaultPrinter();
        }
    }

}