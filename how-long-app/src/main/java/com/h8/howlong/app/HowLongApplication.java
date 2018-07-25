package com.h8.howlong.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.h8.howlong.app.configuration.HowLongApplicationContext;
import com.h8.howlong.app.services.PrintingService;
import com.h8.howlong.app.services.PrintingServiceFactory;
import com.h8.howlong.app.utils.ArgumentResolver;
import com.h8.howlong.utils.Logger;

public class HowLongApplication {

    private static final HowLongApplicationContext applicationContext;

    static {
        Injector injector = Guice.createInjector();
        applicationContext = injector.getInstance(HowLongApplicationContext.class);
    }

    public static void main(String[] args) {
        applicationContext.getTimesheetService().updateWorkDayEndTime();
        ArgumentResolver arguments = new ArgumentResolver(args);
        PrintingService service = resolvePrinter(arguments);
        Logger.log(service.print(arguments.calendarMonth()));
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