package com.h8.howlong;

public class HowLongApplication {

    private static final HowLongApplicationContext applicationContext;

    static {
        applicationContext = new HowLongApplicationContext();
    }

    public static void main(String[] args) {
        applicationContext.getTimesheetService().updateWorkDay();
        ArgumentResolver arguments = new ArgumentResolver(args);
        String response;
        if (arguments.calendarMode()) {
            response = applicationContext.getPrintingServiceFactory()
                    .getCalendarPrinter().print(arguments.calendarMonth());
        } else if (arguments.listMode()) {
            response = applicationContext.getPrintingServiceFactory()
                    .getListPrinter().print(arguments.calendarMonth());
        } else {
            response = applicationContext.getPrintingServiceFactory()
                    .getDefaultPrinter().print(arguments.calendarMonth());
        }
        System.out.print(response);
    }

}