package com.h8.howlong.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.h8.howlong.app.configuration.HowLongApplicationContext;
import com.h8.howlong.app.printers.PrintingService;
import com.h8.howlong.app.printers.PrintingServiceFactory;
import com.h8.howlong.app.utils.ArgumentResolver;

public class HowLongApplication {

    private static final HowLongApplicationContext applicationContext;

    static {
        Injector injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongApplicationContext.class);
    }

    public static void main(String[] args) {
        applicationContext.getTimesheetService().updateWorkDay();
        ArgumentResolver arguments = new ArgumentResolver(args);
        PrintingService service = resolvePrinter(arguments);
        System.out.print(service.print(arguments.calendarMonth()));
    }

    private static PrintingService resolvePrinter(ArgumentResolver arguments) {
        PrintingServiceFactory factory = applicationContext.getPrintingServiceFactory();
        if (arguments.quietMode()) {
            return factory.getQuietPrinter();
        } else if (arguments.calendarMode()) {
            return factory.getCalendarPrinter();
        } else if (arguments.listMode()) {
            return factory.getListPrinter();
        } else {
            return factory.getDefaultPrinter();
        }
    }

}